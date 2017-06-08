package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import com.fuckSpring.service.pollService.proxyRelated.HttpProxySpider;

import java.util.List;

/**
 * Created by upsmart on 17-6-8.
 */
public class XicidailiService implements HttpProxySpider {
    @Override
    public String createProxyUrl(int pageNum) {
        return null;
    }

    @Override
    public List<IpInfoDO> getProxyIp(String url) {
        return null;
    }
}
