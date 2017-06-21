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

//    String getStringByGet(String url);

    List<IpInfoDO> parseBody(String body);

//    //在没有控制器的情况下，由各代理自行发送到消息队列
//    void sendToProxyPool(IpInfoDO ipInfoDO);

//    List<IpInfoDO> spiderRun();
}
