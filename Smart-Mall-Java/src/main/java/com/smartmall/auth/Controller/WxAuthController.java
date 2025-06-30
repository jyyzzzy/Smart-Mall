package com.smartmall.auth.controller;

import com.smartmall.auth.service.AuthService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
@RequestMapping("/wx")
public class WxAuthController {

    private final AuthService authService;

    record LoginReq(@NotBlank String code) {}
    record RefreshReq(@NotBlank String refreshToken) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        var bundle = authService.loginWithWx(req.code());
        return ResponseEntity.ok(bundle);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshReq req) {
        var bundle = authService.refresh(req.refreshToken());
        return ResponseEntity.ok(bundle);
    }
}
