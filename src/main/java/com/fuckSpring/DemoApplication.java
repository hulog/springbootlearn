package com.fuckSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
//		String s = "\\u4eb2~\\u6162\\u70b9\\uff0c\\u64cd\\u4f5c\\u592a\\u5feb\\u4e86\\uff01";
//		String[] split = s.split("\\\\u");
//		System.out.println(Arrays.toString(split));
//		split[1] = split[1].substring(0, 4);
//		System.out.println(Arrays.toString(split));
//		for (int i = 1; i < split.length; i++) {
//			char ch = (char) Integer.parseInt(split[i], 16);
//			System.out.print(ch);
//		}

		SpringApplication.run(DemoApplication.class, args);
	}
}
