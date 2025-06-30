package com.smartmall.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity @Getter @Setter @NoArgsConstructor
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Instant expiryTime;
}
