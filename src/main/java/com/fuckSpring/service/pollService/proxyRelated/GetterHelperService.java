package com.fuckSpring.service.pollService.proxyRelated;


import com.fuckSpring.domain.pollRelated.IpInfoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * 1. 用于检测代理池中IP数量是否足够（undo）
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
    private KdlGetterService kdlGetterService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void task() {
        run();
    }

    public void run() {
        sendToProxyPool(spiderRun(kdlGetterService));
    }

    private void sender(IpInfoDO ipInfoDO) {
        this.amqpTemplate.convertAndSend("Ip_Proxy_Queue",ipInfoDO);
    }

    private void sendToProxyPool(List<IpInfoDO> ips) {
        ips.forEach(this::sender);
        logger.info("已将本批次共 {} 个代理发送到消息队列中...", ips.size());
    }

    private List<IpInfoDO> spiderRun(GetterService gs) {
        logger.info("Start=================={}===========================", gs.getClass().getName());
        List<IpInfoDO> rstList = new ArrayList<>();
        List<IpInfoDO> proxyIps;
        int currentPageNum = 0;
        while (currentPageNum < gs.getMaxPage()) {
            currentPageNum++;
            String spiderUrl = gs.createSpiderUrl(currentPageNum);
            proxyIps = gs.parseBody(gs.getHtmlByUrl(spiderUrl));
            if (null == proxyIps || proxyIps.size() == 0) {
                continue;
            }
            logger.info("在 {} 中爬取到 {} 条数据", spiderUrl, proxyIps.size());
            rstList.addAll(proxyIps);
        }
//            if (current_page == 1) {
//                String kdl01 = this.stringRedisTemplate.opsForValue().get("kdl01");
//                if (null != kdl01 && kdl01.equals(proxyIp.get(0).getIp())) {
//                    current_page = 0;
//                    logger.info(">>快代理<< 代理未更新,请等待......");
//                } else {
//                    logger.info(">>快代理<< 数据已更新,爬取中......");
//                    this.stringRedisTemplate.opsForValue().set("kdl01", proxyIp.get(0).getIp());
//                    this.amqpTemplate.convertAndSend("Ip_Proxy_Queue", proxyIp);
//                }
//            } else {
//                this.amqpTemplate.convertAndSend("Ip_Proxy_Queue", proxyIp);
//            }
        logger.info("End=================={}===========================", gs.getClass().getName());
        return rstList;
    }

}
