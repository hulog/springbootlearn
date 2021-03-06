package com.fuckSpring.controller;

import com.fuckSpring.domain.User;
import com.fuckSpring.domain.paramBean.UserRequestVO;
import com.fuckSpring.service.UserService;
import com.fuckSpring.service.pollService.xinfulaiqiaomen.HttpSenderTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by upsmart on 17-5-11.
 */
@Api(description = "TestController接口总入口")
@RestController
@RequestMapping("/")
public class HelloBootController {

    private final Logger logger = LoggerFactory.getLogger(HelloBootController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSenderTestService httpSenderTestService;

    @ApiOperation(value = "获取服务器返回头内容", notes = "从服务器返回的Header中解析")
    @RequestMapping(value = "serverHeaderContent", method = RequestMethod.GET)
    public String getNginxVersion(@RequestParam(value = "url") String url,
                                  @RequestParam(value = "headerName") String headerName) {
        try {
            return this.httpSenderTestService.getHeaderValueByName(url, headerName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
//
//    @RequestMapping(value = "pollForXinFuLaiQiaoMen", method = RequestMethod.GET)
//    public boolean pollForXinFuLaiQiaoMen() throws IOException, InterruptedException {
//        boolean flag;
//        flag = this.xinfulaiqiaomenService.startPolling();
//        return flag;
//    }


    @ApiOperation(value = "测试接口", notes = "第一个接口")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @Cacheable(value = "cache1", keyGenerator = "wiselyKeyGenerator")
    public String test() {
        logger.info("enter test()");
        logger.info("缓存miss");
        return "Hello Spring Boot!";
    }

    @ApiOperation(value = "创建用户", notes = "第二个接口")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody UserRequestVO userRequestVO) {
        return this.userService.crtUser(userRequestVO.getName(), userRequestVO.getAge());
    }

    @ApiOperation(value = "获取用户", notes = "第三个接口")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User findUser(@ApiParam("用户名") @RequestParam String name) {
        return this.userService.findByName(name);
    }
}
