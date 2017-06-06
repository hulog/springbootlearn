package com.fuckSpring.service.pollService;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by upsmart on 17-6-5.
 */
public interface PollService {

//    // 构造Get请求并发送
//    Request makeGetRequestAndSend(String url, Headers headers);
//
//    // 构造Post请求并发送
//    Request makePostRequestAndSend(String url, Headers headers, RequestBody requestBody);

    // 开始投票
    void startPolling();

    // 验证每次投票是否成功
    boolean isPollSuccessful();
}
