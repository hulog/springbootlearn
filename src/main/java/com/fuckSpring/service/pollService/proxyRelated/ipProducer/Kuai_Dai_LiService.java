package com.fuckSpring.service.pollService.proxyRelated.ipProducer;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import com.fuckSpring.service.pollService.proxyRelated.HttpProxySpider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by upsmart on 17-6-6.
 */
@Service
public class Kuai_Dai_LiService implements HttpProxySpider {

    private static final Logger logger = LoggerFactory.getLogger(Kuai_Dai_LiService.class);

    @Autowired
    private OkHttpClient okHttpClient;

    public static final String HREF = "http://www.kuaidaili.com/free/inha/";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0";

    private int count = 0;

    @Override
    public String createProxyUrl(int pageNum) {
        return HREF + pageNum + "/";
    }

    @Scheduled(fixedDelay = 5000)
    public void run(){
        logger.info("=================================================");
        getProxyIp(createProxyUrl(count++));
    }

    @Override
    public List<IpInfoDO> getProxyIp(String url) {
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
            if (null != rsp) {
                doc = rsp.body().string();
                rstList = parse(doc);
                logger.info(">>快代理<< 在{}中爬取到 {} 条数据", url, rstList.size());
            }
        } catch (IOException e) {
            logger.error("!!!!!Ops,爬取IP出错:{}", e.getMessage());
        }
        return rstList;
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
            rstList.add(infoDO);
        });
        return rstList;
    }
}
