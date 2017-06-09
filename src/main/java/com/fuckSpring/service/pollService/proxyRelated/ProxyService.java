package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;

import java.util.List;

/**
 * Created by upsmart on 17-6-6.
 */
public interface ProxyService {

    String createSpiderUrl(int pageNum);

    String getHtmlByUrl(String url);

    List<IpInfoDO> parseBody(String body);

    void sendToProxyPool(IpInfoDO ipInfoDO);

    void spiderRun();


//    List<IpInfoDO> getProxyIps(String url);

//    boolean isTestOk(IpInfoDO ipInfoDO);
}
