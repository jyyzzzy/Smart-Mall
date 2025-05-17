package com.smartmall.controller;

import com.smartmall.domain.*;
import com.smartmall.service.CustomerService;
import com.smartmall.service.MallService;
import com.smartmall.service.MerchantService;
import com.smartmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MallService mallService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/find-all")
    public Result list(@RequestBody User user) {
        List<User> userList = userService.findAll();
        return Result.success(userList);
    }

    @GetMapping("/user/login")
    public Result login(User user) {
        List<User> userList = userService.selectUserByUserNameAndPassword(user);
        if (userList != null && !userList.isEmpty()) {
            return Result.success(userList.getFirst());
        } else {
            return Result.error("error");
        }
    }

    @GetMapping("/user")
    public Result selectUserList(User user) {
        List<User> userList = userService.selectUserList(user);
        return Result.success(userList);
    }

    @PostMapping("/user")
    public Result insertUser(@RequestBody User user) {
        userService.insertUser(user);
        return Result.success();
    }

    @PostMapping("/user/register")
    public Result register(@RequestBody User user) {
        List<User> userList = userService.selectUserByUserName(user);
        if (userList.isEmpty()) {
            userService.insertUser(user);
            switch (user.getRole()) {
                case "admin" -> {
                }
                case "mall" -> {
                    Mall mall = new Mall();
                    mall.setUserId(user.getUserId());
                    mall.setMallName("Unnamed");
                    mallService.insertMall(mall);
                }
                case "merchant" -> {
                    Merchant merchant = new Merchant();
                    merchant.setUserId(user.getUserId());
                    merchant.setMerchantName("Unnamed");
                    merchantService.insertMerchant(merchant);
                }
                case "customer" -> {
                    Customer customer = new Customer();
                    customer.setUserId(user.getUserId());
                    customer.setCustomerName("Unnamed");
                    customerService.insertCustomer(customer);
                }
            }
        } else {
            return Result.error("Already exist!");
        }
        return Result.success();
    }

    @PutMapping("/user")
    public Result updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

    @DeleteMapping("/user")
    public Result deleteUserByUserId(@RequestBody User user) {
        userService.deleteUserByUserId(user.getUserId());
        return Result.success();
    }
}
