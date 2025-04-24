package com.smartmall.controller;

import com.smartmall.domain.Result;
import com.smartmall.domain.User;
import com.smartmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public Result list(User user) {
        List<User> userList = userService.findAll();
        return Result.success(userList);
    }
}
