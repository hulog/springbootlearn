package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public static final String HREF = "http://www.kuaidaili.com/free/inha/";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0";

    private static int count = 0;

    @Override
    public String createSpiderUrl(int pageNum) {
        return HREF + pageNum + "/";
    }

    @Scheduled(fixedDelay = 10 * 1000)
    public void run() {
        logger.info("=================================================");
        List<IpInfoDO> proxyIp = getProxyIps(createSpiderUrl(count < 20 ? ++count : (count = 1)));
        if (count == 1) {
            String kdl01 = this.stringRedisTemplate.opsForValue().get("kdl01");
            if (null != kdl01 && kdl01.equals(proxyIp.get(0).getIp())) {
                count = 0;
                logger.info(">>快代理<< 代理未更新,请等待......");
            } else {
                logger.info(">>快代理<< 数据已更新,爬取中......");
                this.stringRedisTemplate.opsForValue().set("kdl01", proxyIp.get(0).getIp());
                this.amqpTemplate.convertAndSend("Ip_Proxy_Queue", proxyIp);
            }
        } else {
            this.amqpTemplate.convertAndSend("Ip_Proxy_Queue", proxyIp);

        }
    }

    @Override
    public List<IpInfoDO> getProxyIps(String url) {
        List<IpInfoDO> rstList = null;
        Request req = new Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .get()
                .build();
        Response rsp;
        String doc;
        try {
            rsp = this.okHttpClient.newCall(req).execute();
            if (null != rsp && rsp.isSuccessful()) {
                doc = rsp.body().string();
                rstList = parse(doc);
                logger.info(">>快代理<< 在 {} 中爬取到 {} 条可用代理", url, rstList.size());
            }
        } catch (IOException e) {
            logger.error("!!!!!Ops,爬取IP出错:{}", e.getMessage());
        }
        return rstList;
    }

    @Override
    public boolean isTestOk(IpInfoDO ipInfoDO) {

        return false;
    }

    private List<IpInfoDO> parse(String doc) {
        if (null == doc || doc.length() <= 0) {
            return null;
        }
        List<IpInfoDO> rstList = new ArrayList<>();
        Elements trList = Jsoup.parse(doc)
                .getElementsByTag("tbody")
                //可能获取到多个tbody元素，取第一个
                .get(0)
                //获取tr列表
                .children();

        trList.stream().forEach(tr -> {
            IpInfoDO infoDO = new IpInfoDO();
            String ip = tr.child(0).text();
            String port = tr.child(1).text();
            String addr = tr.child(4).text();
            infoDO.setIp(ip);
            infoDO.setPort(Integer.parseInt(port));
            infoDO.setAddr(addr);
            if (isTestOk(infoDO)) {
                rstList.add(infoDO);
            }
        });
        return rstList;
    }
}
