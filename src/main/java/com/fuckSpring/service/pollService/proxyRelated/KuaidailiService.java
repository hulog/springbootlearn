package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by upsmart on 17-6-6.
 */
@Service
public class KuaidailiService implements ProxyService {

    private static final Logger logger = LoggerFactory.getLogger(ProxyService.class);

    @Qualifier("getClient")
    @Autowired
    private OkHttpClient okHttpClient;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String HREF = "http://www.kuaidaili.com/free/inha/";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0";

    private static final int MAX_PAGE = 20;
    private int current_page = 0;

    @Override
    public String createSpiderUrl(int pageNum) {
        return HREF + pageNum + "/";
    }

    @Override
    public String getHtmlByUrl(String url) {
        //因为url中协议就占了4位，所以判断length<5
        if (null == url || url.length() < 5 || !url.startsWith("http")) {
            return null;
        }
        Request req = new Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .get()
                .build();
        Response rsp;
        String html = null;
        try {
            rsp = this.okHttpClient.newCall(req).execute();
            if (null != rsp && rsp.isSuccessful()) {
                ResponseBody body = rsp.body();
                if (body != null) {
                    html = body.string();
                }
            }
        } catch (Exception e) {
            logger.error("!!!!!Ops,爬取url: {} 出错:{}", url, e.getMessage());
        }
        return html;
    }

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
            String addr = tr.child(4).text();
            infoDO.setIp(ip);
            infoDO.setPort(Integer.parseInt(port));
            infoDO.setAddr(addr);
            rstList.add(infoDO);
        });
        return rstList;
    }

    @Override
    public void sendToProxyPool(IpInfoDO ipInfoDO) {
    }

    @Override
    public void spiderRun() {
        logger.info("Start==================Kuai==Dai==Li===========================");
        while (current_page < MAX_PAGE) {
            current_page++;
            List<IpInfoDO> proxyIp = parseBody(getHtmlByUrl(createSpiderUrl(current_page)));
            proxyIp.forEach(this::sendToProxyPool);
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
        logger.info("End==================Kuai==Dai==Li===========================");
    }
}