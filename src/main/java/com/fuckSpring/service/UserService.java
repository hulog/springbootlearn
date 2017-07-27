package com.fuckSpring.service;

import com.fuckSpring.domain.User;
import com.fuckSpring.repository.UserRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by upsmart on 17-5-11.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //    @Cacheable(value = "usercache",)
    public User crtUser(String name, int age) {
        User user = new User();
        user.setAge(age);
        user.setName(name);

        User user1 = this.userRepository.save(user);
        if (user1 != null) {
            System.out.println("I'm here");
            return user1;
        }
        return null;
    }

    public User findByName(String name) {
        this.userRepository.findOne(2);
        return this.userRepository.findByName(name);
    }
}
