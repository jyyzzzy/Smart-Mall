package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
//用户信息
public class User {
    private String userId;
    private String userName;
    private String password;
    private String role;
    private String phone;
    private String email;
    private Date createdAt;
    private Date updatedAt;
}
