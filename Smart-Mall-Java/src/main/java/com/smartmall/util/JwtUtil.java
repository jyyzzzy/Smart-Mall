package com.smartmall.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-ttl-ms}")
    private long accessTtl;
    @Value("${jwt.refresh-ttl-ms}")
    private long refreshTtl;
    private static final String ROLE = "role";

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /* ===== generate ===== */
    public String accessToken(String sub, String role) {
        return Jwts.builder()
                .issuer("smartmall")
                .subject(sub)
                .claim(ROLE, role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTtl))
                .signWith(key())
                .compact();
    }
    public String refreshToken(String sub) {
        return Jwts.builder()
                .issuer("smartmall")
                .subject(sub)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTtl))
                .signWith(key())
                .compact();
    }

    /* ===== parse ===== */
    private Claims claims(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload();
    }
    public String subject(String token) {return claims(token).getSubject();}
    public String role(String token) {return claims(token).get(ROLE, String.class);}
    public boolean expired(String token) {return claims(token).getExpiration().before(new Date());}

    /* ===== validate ===== */
    public boolean validate(String token, UserDetails ud) {
        return ud.getUsername().equals(subject(token)) && !expired(token);
    }
}