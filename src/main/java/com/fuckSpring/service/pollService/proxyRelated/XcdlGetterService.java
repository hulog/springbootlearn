package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;

import java.util.List;

/**
 * Created by upsmart on 17-6-8.
 */
public class XcdlGetterService implements GetterService {

    @Override
    public int getMaxPage() {
        return 0;
    }

    @Override
    public String createSpiderUrl(int pageNum) {
        return null;
    }

    @Override
    public List<IpInfoDO> parseBody(String body) {
        return null;
    }

//    @Override
//    public void sendToProxyPool(IpInfoDO ipInfoDO) {
//
//    }
}
