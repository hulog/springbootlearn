package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import com.fuckSpring.util.okhttp.OkHttpUtils_bak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class abstractGetService implements GetterService {

    static final Logger logger = LoggerFactory.getLogger(GetterService.class);

    @Override
    public void execute() {

        int currentPage = 0;
        String urlStr;
        List<IpInfoDO> proxyIps;

        while (currentPage < getMaxPage()) {
            urlStr = createSpiderUrl(currentPage++);
            proxyIps = parseBody(OkHttpUtils_bak.getStringByGet(urlStr));
            if (null == proxyIps || proxyIps.size() == 0) {
                continue;
            }
            // TODO 发送到缓冲池
            // send(proxyIps);
        }
    }

    abstract int getMaxPage();

    abstract String createSpiderUrl(int pageNum);

    abstract List<IpInfoDO> parseBody(String body);
}
