package com.fuckSpring.service.pollService;

import com.fuckSpring.domain.pollRelated.IpInfoDO;

import java.util.List;

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
    void startPolling(List<IpInfoDO> list);

    // 验证每次投票是否成功
    boolean isPollSuccessful(IpInfoDO ipInfoDO);
}
