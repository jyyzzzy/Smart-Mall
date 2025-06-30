package com.smartmall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户实体：新增 sanitize() 用于脱敏返回（去掉密码等敏感字段）。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String username;
    private String password;
    private String role;
    private String phone;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    /**
     * 脱敏副本：复制当前对象但清空 password。
     * 用于 API 响应避免泄露加密后的密码串。
     */
    public User sanitize() {
        return new User(
                this.userId,
                this.username,
                null,          // 不返回密码
                this.role,
                this.phone,
                this.email,
                this.createdAt,
                this.updatedAt);
    }
}
