package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;

import java.util.List;

/**
 * @Description: Proxy 爬取接口，爬取各大代理网站都需实现此接口
 * @Date: 17-6-12
 * @Time: 下午3:41
 * @Author: hl
 */
public interface GetterService {

    int getMaxPage();

    String createSpiderUrl(int pageNum);

//    String getHtmlByUrl(String url);

    List<IpInfoDO> parseBody(String body);

    void sendToProxyPool(IpInfoDO ipInfoDO);

//    List<IpInfoDO> spiderRun();
}
