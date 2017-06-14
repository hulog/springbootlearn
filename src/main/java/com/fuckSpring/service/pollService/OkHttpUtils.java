package com.fuckSpring.service.pollService;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by upsmart on 17-6-13.
 */
@Component
public final class OkHttpUtils {

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0";

    private static Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    public static String getHtmlByUrl(String url) {
        if (null == url || url.length() < 5 || !url.startsWith("http")) {
            return null;
        }
        Headers headers = new Headers.Builder()
                .add("User-Agent", USER_AGENT)
                .build();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .headers(headers)
                .build();
        Response rsp;
        String html = null;
        try {
            rsp = okHttpClient.newCall(request).execute();
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

}
