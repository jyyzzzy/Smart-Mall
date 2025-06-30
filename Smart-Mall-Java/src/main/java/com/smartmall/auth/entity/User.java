package com.smartmall.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String openid;

    @Column(unique = true)
    private String unionid;

    private String nickname;
    private String avatarUrl;
    private String role = "customer";     // 默认角色
}
