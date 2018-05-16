package com.sixi.oaplatform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试登录
 * @author wangwei
 * @date 2018/3/30
 */
@RequestMapping("/hello")
@RestController
public class HelloController {


    @RequestMapping("/service")
    public String helloService(){
        return "hello";
    }

}
