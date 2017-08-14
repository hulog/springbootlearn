package com.fuckSpring.service.pollService.proxyRelated;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 1. 用于检测代理池中IP数量是否足够（undo）
 * 2. 检测代理的有效性
 * 3. 定期爬取
 * @Date: 17-6-12
 * @Time: 下午3:41
 * @Author: hl
 */
@Service
public class ProxyController {

    @Autowired
    private List<GetterService> getterServices;

    private Logger logger = LoggerFactory.getLogger(ProxyController.class);

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // @Scheduled(fixedDelay = 1 * 5 * 1000)
    public void task() {
        spiderStartController();
    }

    public void spiderStartController() {
        System.out.println("print start");
        for (GetterService gs : getterServices) {
            System.out.println(gs.toString());
            // gs.execute();
        }
        System.out.println("print end");
    }
}