package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;

import java.util.List;

/**
 * Created by upsmart on 17-6-6.
 */
public interface HttpProxySpider {

    String createProxyUrl(int pageNum);

    List<IpInfoDO> getProxyIp(String url);
}
