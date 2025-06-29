package com.example.demo;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SpringbootMybatisTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void getAllUser(){
        List<User> users = userMapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void deleteUserById(){
        Integer count = userMapper.deleteUser(5);
        System.out.println(count);
    }

    @Test
    public void addUser(){
        User user = new User(null, "yase", "123456", "亚瑟", 18);
        Integer count = userMapper.addUser(user);
        System.out.println(count);
    }

    @Test
    public void updateUser(){
        User user = new User(6, "yase", "123456", "亚瑟", 43);
        Integer count = userMapper.updateUser(user);
        System.out.println(count);
    }

    @Test
    public void testFindByUsernameAndPassword(){
        User user = userMapper.findUserByIdAndPassword(6, "123456");
        System.out.println(user);
    }
}
