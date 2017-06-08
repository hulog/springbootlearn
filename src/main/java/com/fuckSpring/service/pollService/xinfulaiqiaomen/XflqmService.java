package com.fuckSpring.service.pollService.xinfulaiqiaomen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fuckSpring.domain.pollRelated.IpInfoDO;
import com.fuckSpring.service.pollService.PollService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by upsmart on 17-6-5.
 */
@Service
public class XflqmService implements PollService{

    private static final Logger logger = LoggerFactory.getLogger(XflqmService.class);
    private static final String URL1 = "http://vote.gzdsb.net/vote/index/load.html?rnd=&pid=5&id=42&callback=?";
    private static final String URL2 = "http://vote.gzdsb.net/vote/index/vote.html?rnd=&pid=5&id=42&callback=?";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0";


    @Autowired
    private OkHttpClient okHttpClient;

    @Override
    public void startPolling() {
        IpInfoDO ipInfoDO = new IpInfoDO();
        isPollSuccessful(ipInfoDO);
    }

    @Override
    public boolean isPollSuccessful(IpInfoDO ipInfoDO) {
        boolean flag = false;
        //第一次请求
        Request req = new Request.Builder()
                .header("User-Agent", USER_AGENT)
                .url(URL1)
                .get()
                .build();
        Response rsp = this.okHttpClient.newCall(req).execute();
        JSONObject jsonObject;
        if (rsp.isSuccessful()) {
            String rstString = rsp.body().string();
            logger.info("第一次请求，返回数据:{}", rstString);
            //json串包含在：?(json)中
            jsonObject = JSON.parseObject(rstString.substring(2, rstString.length() - 1));
            if (null != jsonObject) {
                String hash = (String) jsonObject.get("hash");
                if (null != hash && hash.length() > 0) {
                    logger.info("成功解析出hash:{},等待3s...", hash);
                    Thread.sleep(3000);
                    //第二次请求
                    flag = this.pollHelper(hash);
                } else {
                    flag = false;
                    logger.warn("未解析出hash!");
                }
            } else {
                flag = false;
                logger.warn("解析第一次请求返回数据出错");
            }
        } else {
            logger.info("第一次请求失败");
        }
        return flag;
    }


    public boolean pollHelper(String hash) throws IOException {
        boolean flag = false;
        Request req = new Request.Builder()
                .url(URL2 + "&hash=" + hash)
                .header("User-Agent", USER_AGENT)
                .build();
        Response rsp;
        rsp = this.okHttpClient.newCall(req).execute();
        if (rsp.isSuccessful()) {
            String rstString = rsp.body().string();
            logger.info("第二次请求，返回数据:{}", rstString);
            JSONObject jsonObject = JSON.parseObject(rstString.substring(2, rstString.length() - 1));
            int status = (int) jsonObject.get("status");
            switch (status) {
                case -1: {
                    logger.warn("动作太快");
                    flag = false;
                    break;
                }
                case 1: {
                    logger.info("投票成功");
                    flag = true;
                    break;
                }
                case -90: {
                    logger.warn("当前用户投票次数耗尽");
                    flag = false;
                    break;
                }
                default: {
                    logger.warn("未解析出当前状态码:{}", status);
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
}
