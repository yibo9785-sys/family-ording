package com.example.family_ording.controller.user;

import com.example.family_ording.Result;
import com.example.family_ording.model.entity.User;
import com.example.family_ording.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public Result<User> getCurrentUserProfile() {
        try {
            User currentUser = getCurrentUser();
            if (currentUser == null) {
                log.warn("用户未登录");
                return Result.error(401, "用户未登录");
            }
            return Result.success(currentUser);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return Result.error(500, "获取用户信息失败");
        }
    }

    @PutMapping("/profile")
    public Result<User> updateCurrentUserProfile(@RequestBody User user) {
        try {
            User currentUser = getCurrentUser();
            if (currentUser == null) {
                log.warn("用户未登录");
                return Result.error(401, "用户未登录");
            }

            user.setId(currentUser.getId());
            user.setUsername(currentUser.getUsername());
            user.setPassword(currentUser.getPassword());
            user.setRole(currentUser.getRole());

            boolean updated = userService.updateById(user);
            if (updated) {
                log.info("用户信息更新成功：{}", currentUser.getUsername());
                return Result.success(user);
            } else {
                log.error("用户信息更新失败：{}", currentUser.getUsername());
                return Result.error(500, "更新用户信息失败");
            }
        } catch (Exception e) {
            log.error("更新用户信息异常", e);
            return Result.error(500, "更新用户信息失败");
        }
    }

    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody PasswordUpdateRequest request) {
        try {
            User currentUser = getCurrentUser();
            if (currentUser == null) {
                log.warn("用户未登录");
                return Result.error(401, "用户未登录");
            }

            if (request.getOldPassword() == null || request.getOldPassword().isEmpty()) {
                log.warn("原密码为空：{}", currentUser.getUsername());
                return Result.error(400, "原密码不能为空");
            }

            if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
                log.warn("新密码为空：{}", currentUser.getUsername());
                return Result.error(400, "新密码不能为空");
            }

            if (request.getNewPassword().length() < 6 || request.getNewPassword().length() > 20) {
                log.warn("新密码长度不符合要求：{}", currentUser.getUsername());
                return Result.error(400, "新密码长度必须在6-20之间");
            }

            log.info("用户修改密码：{}", currentUser.getUsername());
            boolean updated = userService.updateById(currentUser);
            if (updated) {
                log.info("密码修改成功：{}", currentUser.getUsername());
                return Result.success(null);
            } else {
                log.error("密码修改失败：{}", currentUser.getUsername());
                return Result.error(500, "修改密码失败");
            }
        } catch (Exception e) {
            log.error("修改密码异常", e);
            return Result.error(500, "修改密码失败");
        }
    }

    private User getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return null;
            }
            String username = authentication.getName();
            return userService.getUserByUsername(username);
        } catch (Exception e) {
            log.error("获取当前用户失败", e);
            return null;
        }
    }

    static class PasswordUpdateRequest {
        private String oldPassword;
        private String newPassword;

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

}
