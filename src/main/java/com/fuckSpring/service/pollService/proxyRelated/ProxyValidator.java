package com.fuckSpring.service.pollService.proxyRelated;

import com.fuckSpring.domain.pollRelated.IpInfoDO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by upsmart on 17-6-12.
 */
@Component
public class ProxyValidator {

    @Autowired
    private ExecutorService es = Executors.newFixedThreadPool(50);
    private StringRedisTemplate stringRedisTemplate;
    private static AtomicInteger count = new AtomicInteger(0);
    private static AtomicInteger su = new AtomicInteger(0);

    @RabbitHandler
    void run(IpInfoDO ipInfoDO) throws IOException {
        System.out.println("总共测试IP个数" + count.incrementAndGet());
        OkHttpClient client = new OkHttpClient.Builder()
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipInfoDO.getIp(), ipInfoDO.getPort())))
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
//                    .url("http://ec2-54-178-184-97.ap-northeast-1.compute.amazonaws.com/")
                .url("http://ip.chinaz.com/getip.aspx/")
                .get()
                .build();
        Response execute;
        try {
            execute = client.newCall(request).execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
//        System.out.println(execute.code());
        if (execute.isSuccessful()) {
//                System.out.println(execute.body().bytes());
            System.out.println(execute.body().string());
            System.out.println("成功次数：" + su.incrementAndGet());
            stringRedisTemplate.opsForValue().set(String.valueOf(su), ipInfoDO.toString());
        } else {
            System.out.println("失败次数了");
        }

        System.out.println("成功率：" + su.get() * 1.0 / count.get() * 100 + "%");
    }
}