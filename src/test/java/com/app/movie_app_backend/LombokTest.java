package com.app.movie_app_backend;

import com.app.movie_app_backend.model.UserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LombokTest {

    @Test
    void testLombokAnnotations() {
        // Test @Builder
        UserInfo user = UserInfo.builder()
                .username("testUser")
                .email("test@example.com")
                .password("testPassword")
                .build();

        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("testPassword", user.getPassword());

        // Test @Data (Getters and Setters)
        user.setUsername("updatedUser");
        assertEquals("updatedUser", user.getUsername());
    }
}