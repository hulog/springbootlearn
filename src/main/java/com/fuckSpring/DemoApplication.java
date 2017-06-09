package com.fuckSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

        public static void main(String[] args) throws IOException {
        SpringApplication.run(DemoApplication.class, args);
    }
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("60.178.1.116", 8081)))
////                    .connectTimeout(20, TimeUnit.SECONDS)
////                    .readTimeout(20, TimeUnit.SECONDS)
//                    .build();
//
//            Request request = new Request.Builder()
////                    .url("http://ec2-54-178-184-97.ap-northeast-1.compute.amazonaws.com/")
//                    .url("http://www.baidu.com/")
//                    .get()
//                    .build();
//            Response execute = client.newCall(request).execute();
//            if (execute.isSuccessful()) {
////                System.out.println(execute.body().bytes());
//                System.out.println(execute.body().string());
//            }
//        }
}
