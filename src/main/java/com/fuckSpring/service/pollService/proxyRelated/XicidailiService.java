package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;

import java.util.List;

/**
 * Created by upsmart on 17-6-8.
 */
public class XicidailiService implements ProxyService {

    @Override
    public String createSpiderUrl(int pageNum) {
        return null;
    }

    @Override
    public String getHtmlByUrl(String url) {
        return null;
    }

    @Override
    public List<IpInfoDO> parseBody(String body) {
        return null;
    }

    @Override
    public void sendToProxyPool(IpInfoDO ipInfoDO) {

    }

    @Override
    public void spiderRun() {

    }
}
