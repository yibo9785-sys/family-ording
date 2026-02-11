package com.example.family_ording.service;

import com.example.family_ording.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User getUserByUsername(String username);
    User getUserById(Long id);
    boolean save(User user);
}
