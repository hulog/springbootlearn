package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;

import java.util.List;

/**
 * Proxy 爬取接口，爬取各大代理网站都需实现此接口
 *
 * Created by upsmart on 17-6-6.
 */
public interface GetterService {

    String createSpiderUrl(int pageNum);

    String getHtmlByUrl(String url);

    List<IpInfoDO> parseBody(String body);

//    void sendToProxyPool(IpInfoDO ipInfoDO);

    List<IpInfoDO> spiderRun();
}
