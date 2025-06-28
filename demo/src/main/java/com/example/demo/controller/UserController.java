package com.example.demo.controller;


import com.example.demo.pojo.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    /*
    存在多个UserService实现类时，会不知道注入哪个实现类，有三种解决方法：
        1. 使用@Qualifier("userService")指定注入的实现类
        2. 使用@Primary注解指定默认注入的实现类
        3. 使用@Resource(name = "userService")指定注入的实现类
        @Autowired与@Resource的区别：
            @Autowired是Spring提供的注解，@Resource是JDK提供的注解
            @Autowired默认按类型注入，@Resource默认按名称注入
            @Autowired可以标注在构造方法、属性、方法上，@Resource只能标注在属性上
     */
    @Autowired
    private UserService userService;


    @RequestMapping("/list")
    public List<User> list(){
        return userService.findAll();
    }

}