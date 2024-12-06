package dev.acr.diggingsimulator.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
            "player@example.com", 
            "gamePlayer", 
            "securePassword123", 
            UserRole.PLAYER
        );
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals("player@example.com", user.getEmail());
        assertEquals("gamePlayer", user.getUsername());
        assertEquals(UserRole.PLAYER, user.getRole());
    }

    @Test
    void testSetterMethods() {
        user.setEmail("newemail@example.com");
        user.setUsername("newUsername");
        user.setPassword("newPassword");
        user.setRole(UserRole.ADMIN);

        assertEquals("newemail@example.com", user.getEmail());
        assertEquals("newUsername", user.getUsername());
        assertEquals("newPassword", user.getPassword());
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    void testCreatedAtTimestamp() {
        assertNotNull(user.getCreatedAt());
        assertTrue(user.getCreatedAt().isBefore(LocalDateTime.now()));
    }

    @Test
    void testLastLoginUpdate() {
        LocalDateTime loginTime = LocalDateTime.now();
        user.setLastLogin(loginTime);
        assertEquals(loginTime, user.getLastLogin());
    }
}