package com.smartmall.auth.service;

import com.smartmall.auth.config.JwtProperties;
import com.smartmall.auth.entity.*;
import com.smartmall.auth.repo.*;
import com.smartmall.auth.security.JwtUtil;
import com.smartmall.auth.wechat.WechatApiClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final RefreshTokenRepository refreshRepo;
    private final WechatApiClient wxClient;
    private final JwtUtil jwt;
    private final JwtProperties jwtProps;

    @Transactional
    public TokenBundle loginWithWx(String code) {
        var wx = wxClient.code2session(code);
        if (wx.errcode() != null && wx.errcode() != 0) {
            throw new RuntimeException("wx error: " + wx.errmsg());
        }

        // upsert user
        User user = userRepo.findByOpenid(wx.openid())
                .orElseGet(User::new);
        user.setOpenid(wx.openid());
        user.setUnionid(wx.unionid());
        userRepo.save(user);

        // issue tokens
        String access = jwt.generate(user.getId());
        String refresh = issueRefresh(user);

        return new TokenBundle(access, refresh, user);
    }

    @Transactional
    public TokenBundle refresh(String refreshToken) {
        RefreshToken rt = refreshRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("invalid refresh"));

        if (rt.getExpiryTime().isBefore(Instant.now())) {
            refreshRepo.delete(rt);
            throw new RuntimeException("refresh expired");
        }

        String access = jwt.generate(rt.getUser().getId());
        String newRefresh = issueRefresh(rt.getUser());
        refreshRepo.delete(rt);      // 单次刷新策略

        return new TokenBundle(access, newRefresh, rt.getUser());
    }

    /* ----------- helpers ----------- */
    private String issueRefresh(User user) {
        refreshRepo.deleteByUser(user);                 // 账户仅保留 1 个 token
        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiryTime(Instant.now().plusSeconds(jwtProps.refreshTtl()));
        refreshRepo.save(rt);
        return rt.getToken();
    }

    public record TokenBundle(String accessToken, String refreshToken, User user) {}
}
