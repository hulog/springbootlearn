package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import com.fuckSpring.service.pollService.OkHttpUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by upsmart on 17-6-6.
 */
public final class KdlGetterService implements GetterService {

    private static final Logger logger = LoggerFactory.getLogger(GetterService.class);

    // 爬取代理的目标网站相关
    private static final String HREF = "http://www.kuaidaili.com/proxylist/";

    private static final int MAX_PAGE = 10; //主页只支持10页查询


    @Override
    public int getMaxPage() {
        return MAX_PAGE;
    }

    @Override
    public String createSpiderUrl(int pageNum) {
        if (pageNum < 1) {
            logger.error("[快代理] pageNum应大于1,当前值: {}", pageNum);
            return null;
        }
        logger.info("[快代理] 爬取第 {} 页中......", pageNum);
        return HREF + pageNum + "/";
    }

//    public String getHtmlByUrl(String url) {
//        return OkHttpUtils.getStringByGet(url);
//    }

    @Override
    public List<IpInfoDO> parseBody(String body) {
        if (null == body || body.length() <= 0) {
            return null;
        }
        List<IpInfoDO> rstList = new ArrayList<>();
        Elements trList = Jsoup.parse(body)
                .getElementsByTag("tbody")
                //可能获取到多个tbody元素，取第一个
                .get(0)
                //获取tr列表
                .children();

        trList.forEach(tr -> {
            IpInfoDO infoDO = new IpInfoDO();
            String ip = tr.child(0).text();
            String port = tr.child(1).text();
            String addr = tr.child(5).text();
            infoDO.setIp(ip);
            infoDO.setPort(Integer.parseInt(port));
            infoDO.setAddr(addr);
            rstList.add(infoDO);
        });
        return rstList;
    }

//    @Override
//    public void sendToProxyPool(IpInfoDO ipInfoDO) {
//
//    }

    @Override
    public String toString() {
        return "快代理";
    }

//    @Override
//    public List<IpInfoDO> spiderRun() {
//        logger.info("Start==================Kuai==Dai==Li===========================");
//        List<IpInfoDO> rstList = new ArrayList<>();
//        List<IpInfoDO> proxyIps;
//        current_page = 0;
//        while (current_page < MAX_PAGE) {
//            current_page++;
//            String spiderUrl = createSpiderUrl(current_page);
//            proxyIps = parseBody(getStringByGet(spiderUrl));
//            logger.info("在 {} 中爬取到 {} 条数据", spiderUrl, proxyIps.size());
//            rstList.addAll(proxyIps);
//        }
////            if (current_page == 1) {
////                String kdl01 = this.stringRedisTemplate.opsForValue().get("kdl01");
////                if (null != kdl01 && kdl01.equals(proxyIp.get(0).getIp())) {
////                    current_page = 0;
////                    logger.info(">>快代理<< 代理未更新,请等待......");
////                } else {
////                    logger.info(">>快代理<< 数据已更新,爬取中......");
////                    this.stringRedisTemplate.opsForValue().set("kdl01", proxyIp.get(0).getIp());
////                    this.amqpTemplate.convertAndSend("Ip_Proxy_Queue", proxyIp);
////                }
////            } else {
////                this.amqpTemplate.convertAndSend("Ip_Proxy_Queue", proxyIp);
////            }
//        logger.info("End==================Kuai==Dai==Li===========================");
//        rstList.forEach(System.out::println);
//        return rstList;
//    }
}