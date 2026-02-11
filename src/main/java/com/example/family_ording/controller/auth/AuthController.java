package com.example.family_ording.controller.auth;

import com.example.family_ording.Result;
import com.example.family_ording.model.dto.LoginRequest;
import com.example.family_ording.model.dto.RegisterRequest;
import com.example.family_ording.model.entity.User;
import com.example.family_ording.service.UserService;
import com.example.family_ording.utils.CaptchaUtil;
import com.example.family_ording.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CaptchaUtil captchaUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            log.info("用户注册请求：{}", request.getUsername());

            String captchaKey = "register:" + request.getUsername();

            if (!captchaUtil.verifyCaptcha(captchaKey, request.getCaptcha())) {
                log.warn("验证码错误：{}", request.getUsername());
                return Result.error(400, "验证码错误");
            }

            User existingUser = userService.getUserByUsername(request.getUsername());
            if (existingUser != null) {
                log.warn("用户名已存在：{}", request.getUsername());
                return Result.error(400, "用户名已存在");
            }

            User newUser = new User();
            newUser.setUsername(request.getUsername());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser.setNickname(request.getNickname());
            newUser.setRole(request.getRole());

            boolean saved = userService.save(newUser);//保存用户信息
            if (!saved) {
                log.error("用户注册失败：{}", request.getUsername());
                return Result.error(500, "注册失败");
            }

            captchaUtil.removeCaptcha(captchaKey);

            log.info("用户注册成功：{}", request.getUsername());
            return Result.success(null);
        } catch (Exception e) {
            log.error("注册异常", e);
            return Result.error(500, "注册失败");
        }
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        try {
            log.info("用户登录请求：{}", request.getUsername());

            String captchaKey = "login:" + request.getUsername();

            if (!captchaUtil.verifyCaptcha(captchaKey, request.getCaptcha())) {
                log.warn("验证码错误：{}", request.getUsername());
                return Result.error(400, "验证码错误");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Map<String, Object> claims = new HashMap<>();
            claims.put("username", request.getUsername());

            String token = jwtUtil.generateToken(request.getUsername(), claims);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", request.getUsername());

            captchaUtil.removeCaptcha(captchaKey);

            log.info("用户登录成功：{}", request.getUsername());
            return Result.success(response);
        } catch (Exception e) {
            log.error("登录异常", e);
            return Result.error(401, "用户名或密码错误");
        }
    }

    @GetMapping("/captcha")
    public Result<String> getCaptcha(@RequestParam String username, @RequestParam String type) {
        String key = type + ":" + username;
        String captcha = captchaUtil.generateCaptcha(key);
        return Result.success(captcha);
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        SecurityContextHolder.clearContext();
        return Result.success(null);
    }

}
