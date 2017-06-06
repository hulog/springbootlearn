package com.fuckSpring.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by upsmart on 17-5-27.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloBootControllerTest {

    @Autowired
    private HelloBootController helloBootController;

    @Test
    public void findUser() throws Exception {
//        Assert.assertEquals("www", this.helloBootController.findUser("wjm"));

        this.helloBootController.findUser("wjm");
    }

}