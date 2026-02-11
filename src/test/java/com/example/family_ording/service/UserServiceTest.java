package com.example.family_ording.service;

import com.example.family_ording.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserById() {
        User user = userService.getUserById(1L);
        assertNotNull(user);
    }

    @Test
    public void testGetUserByUsername() {
        User user = userService.getUserByUsername("admin");
        assertNotNull(user);
    }

    @Test
    public void testGetUserByNonExistentId() {
        User user = userService.getUserById(999L);
        assertNull(user);
    }

}
