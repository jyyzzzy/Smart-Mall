package com.smartmall.auth.security;

import com.smartmall.auth.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;                // ① 关键：使用 SecretKey
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;              // ② 字段改为 SecretKey
    private final long ttlSeconds;

    public JwtUtil(JwtProperties props) {
        this.key = Keys.hmacShaKeyFor(
                props.secret().getBytes(StandardCharsets.UTF_8)); // 至少 32 字节
        this.ttlSeconds = props.ttl();
    }

    /** 生成 HS256 签名的 JWT */
    public String generate(Long userId) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(ttlSeconds)))
                .signWith(key, Jwts.SIG.HS256)               // ③ key 已满足上界
                .compact();
    }

    /** 解析并校验签名 */
    private Claims parseClaims(String jwt) {
        return Jwts.parser()                                 // ④ 入口变了
                .verifyWith(key)                             // ⑤ 同样要求 SecretKey
                .build()
                .parseSignedClaims(jwt)
                .getPayload();                               // ⑥ getBody → getPayload
    }

    public Long getUserId(String jwt) {
        return Long.valueOf(parseClaims(jwt).getSubject());
    }
}
