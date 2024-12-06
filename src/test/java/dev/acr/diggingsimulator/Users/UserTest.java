package dev.acr.diggingsimulator.Users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    void testConstructorUser() {
        Long id = 1L;
        String username = "testUser";
        String email = "testUser@gmail.com";
        String password = "testPassword";
        String rol = "admin";
        User user = new User(id, username, email, password, rol);
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(rol, user.getRol());
    }
    @Test
    void testGettersSetters(){
        User user = new User();
        user.setId(1L);
        user.setEmail("testUser@gmail.com");
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRol("admin");
        assertEquals(1L, user.getId());
        assertEquals("testUser@gmail.com", user.getEmail());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
        assertEquals("admin", user.getRol());
    }
}