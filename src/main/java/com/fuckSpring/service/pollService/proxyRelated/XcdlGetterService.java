package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by upsmart on 17-6-8.
 */
@Component
public class XcdlGetterService extends abstractGetService {

    @Override
    int getMaxPage() {
        return 0;
    }

    @Override
    String createSpiderUrl(int pageNum) {
        logger.info("pageNum:" + pageNum);
        return null;
    }

    @Override
    List<IpInfoDO> parseBody(String body) {
        return null;
    }

    public String toString() {
        return "西刺代理";
    }
}