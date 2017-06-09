package com.fuckSpring;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

//@SpringBootApplication
//@EnableScheduling
public class DemoApplication {

        public static void main(String[] args) throws IOException {
//        SpringApplication.run(DemoApplication.class, args);
//    }
            OkHttpClient client = new OkHttpClient.Builder()
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("112.252.84.105", 8998)))
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
//                    .url("http://ec2-54-178-184-97.ap-northeast-1.compute.amazonaws.com/")
                    .url("http://ip.chinaz.com/getip.aspx")
                    .get()
                    .build();
            Response execute = client.newCall(request).execute();
            if (execute.isSuccessful()) {
//                System.out.println(execute.body().bytes());
                System.out.println(execute.code());
                System.out.println(execute.body().string());
            }
        }
}
