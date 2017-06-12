package com.fuckSpring.service.pollService.xinfulaiqiaomen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by upsmart on 17-6-2.
 */
@Service
public class HttpSenderTestService {

    private static final Logger logger = LoggerFactory.getLogger(HttpSenderTestService.class);

    @Qualifier("getClient")
    @Autowired
    private OkHttpClient okHttpClient;

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0";

    public String getHeaderValueByName(String url, String headerName) throws IOException {
        String rstString = null;
        //构建Request
        Request req = new Request.Builder()
                .url(url)
                .build();
        //获取Response
        Response rsp = this.okHttpClient.newCall(req).execute();
        if (!rsp.isSuccessful()) {
            //FIXME help me
            rstString = String.valueOf(rsp.code());
            logger.info("链接不成功,返回码:{}", rsp.code());
            return rstString;
        }
        //获取Headers
        Headers rspHeaders = rsp.headers();
        for (int i = 0; i < rspHeaders.size(); i++) {
            logger.info("当前Header键值为 {}:{}", rspHeaders.name(i), rspHeaders.value(i));
            if (rspHeaders.name(i).equals(headerName)) {
                return rspHeaders.value(i);
            }
        }
        return rstString;
    }


    public JSONObject getBodyOfJsonByGetMethod(String url) throws IOException {
        Request req = new Request.Builder()
                .header("User-Agent", USER_AGENT)
                .url(url)
                .get()
                .build();
        Response rsp = this.okHttpClient.newCall(req).execute();
        if (rsp.isSuccessful()) {
            String rstString = rsp.body().string();
            return JSON.parseObject(rstString);
        }
        logger.info("网络请求失败");
        return null;
    }

    public String getPostBody(String url) {
        Request req = new Request.Builder()
                .header("User-Agent", USER_AGENT)
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), "{\"a\":\"c\"}"))
                .build();
        return null;

    }
}
