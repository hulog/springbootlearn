package com.fuckSpring.service.pollService.proxyRelated;


import com.fuckSpring.domain.pollRelated.IpInfoDO;
import com.fuckSpring.service.pollService.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 1. 用于检测代理池中IP数量是否足够（undo）
 * 2. 检测代理的有效性
 * 3. 定期爬取
 * @Date: 17-6-12
 * @Time: 下午3:41
 * @Author: hl
 */
@Service
public class GetterHelperService {

    private Logger logger = LoggerFactory.getLogger(GetterHelperService.class);

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void task() {
        spiderStartController();
    }

    public void spiderStartController() {
        sendToProxyPool(spiderRun(new KdlGetterService()));
        sendToProxyPool(spiderRun(new XcdlGetterService()));
    }

    private void sender(IpInfoDO ipInfoDO) {
        this.amqpTemplate.convertAndSend("Ip_Proxy_Queue", ipInfoDO);
    }

    private void sendToProxyPool(List<IpInfoDO> ips) {
        ips.forEach(this::sender);
        logger.info("已将本批次共 {} 个代理发送到消息队列中...", ips.size());
    }

    private List<IpInfoDO> spiderRun(GetterService gs) {
        logger.info("Start*******************{}***********************", gs.toString());
        List<IpInfoDO> rstList = new ArrayList<>();
        List<IpInfoDO> proxyIps;
        int currentPageNum = 0;
        while (currentPageNum < gs.getMaxPage()) {
            currentPageNum++;
            String spiderUrl = gs.createSpiderUrl(currentPageNum);
            proxyIps = gs.parseBody(OkHttpUtils.getStringByGet(spiderUrl));
            if (null == proxyIps || proxyIps.size() == 0) {
                continue;
            }
            // 在redis中检测该代理网站IP信息是否更新（redis中数据15min更新一次）
            String ipFlag = this.stringRedisTemplate.opsForValue().get(gs.toString());
            if (currentPageNum == 1 && proxyIps.get(0).getIp().equals(ipFlag)) {
                logger.warn("{} 代理主页未更新，本次爬取结束", gs.toString());
                break;
            } else if (currentPageNum == 1) {
                this.stringRedisTemplate.opsForValue().set(gs.toString(), proxyIps.get(0).getIp());
            }
            logger.info("在 {} 中爬取到 {} 条数据", spiderUrl, proxyIps.size());
            rstList.addAll(proxyIps);
        }
        logger.info("End*******************{}***********************", gs.toString());
        return rstList;
    }

}
