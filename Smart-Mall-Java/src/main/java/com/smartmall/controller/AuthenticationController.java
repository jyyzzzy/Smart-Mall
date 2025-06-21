package com.smartmall.controller;

import com.smartmall.domain.*;
import com.smartmall.dto.LoginDTO;
import com.smartmall.dto.TokenDTO;
import com.smartmall.service.*;
import com.smartmall.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 认证控制器：登录 / 刷新 / 注册
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    /* ----------------- Service 注入 ----------------- */
    @Autowired private UserService userService;
    @Autowired private MallService mallService;
    @Autowired private MerchantService merchantService;
    @Autowired private CustomerService customerService;

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    /* ----------------- 登录 ----------------- */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO dto) {
        User user = userService.findByUsername(dto.username());
        if (user != null && passwordEncoder.matches(dto.password(), user.getPassword())) {
            String at = jwtUtil.accessToken(user.getUsername(), user.getRole());
            String rt = jwtUtil.refreshToken(user.getUsername());
            return Result.success(Map.of(
                    "accessToken", at,
                    "refreshToken", rt,
                    "user", user.sanitize()));
        }
        return Result.error("Invalid username or password");
    }

    /* ----------------- 刷新 Access‑Token ----------------- */
    @PostMapping("/refresh")
    public Result refresh(@RequestBody TokenDTO dto) {
        try {
            String newAt = jwtUtil.refresh(dto.refreshToken());
            return Result.success(Map.of("accessToken", newAt));
        } catch (Exception e) {
            return Result.error("Refresh token invalid");
        }
    }

    /* ----------------- 注册 ----------------- */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (userService.exists(user.getUsername())) {
            return Result.error("Username already exists.");
        }

        // 密码加密存储
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int n = userService.insertUser(user); // useGeneratedKeys 回填 userId
        if (n <= 0) return Result.error("Registration failed.");

        // 根据角色创建关联实体
        switch (user.getRole()) {
            case "mall" -> {
                Mall mall = new Mall();
                mall.setUserId(user.getUserId());
                mall.setMallName(user.getUsername() + "'s Mall");
                mallService.insertMall(mall);
            }
            case "merchant" -> {
                Merchant merchant = new Merchant();
                merchant.setUserId(user.getUserId());
                merchant.setMerchantName(user.getUsername() + "'s Store");
                merchantService.insertMerchant(merchant);
            }
            case "customer" -> {
                Customer customer = new Customer();
                customer.setUserId(user.getUserId());
                customer.setCustomerName(user.getUsername());
                customerService.insertCustomer(customer);
            }
            // admin 或其他角色无需额外操作
        }
        return Result.success("User registered successfully.");
    }
}