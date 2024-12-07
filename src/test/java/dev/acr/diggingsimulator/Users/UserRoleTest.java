package dev.acr.diggingsimulator.Users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    @Test
    void testUserRoleEnumValues() {
        UserRole[] expectedRoles = {UserRole.USER, UserRole.ADMIN};
        assertEquals(2, UserRole.values().length);
        
        for (UserRole role : expectedRoles) {
            assertNotNull(role);
        }
    }

    @Test
    void testUserRoleOrder() {
        UserRole[] roles = UserRole.values();
        assertEquals(UserRole.USER, roles[0]);
        assertEquals(UserRole.ADMIN, roles[1]);
    }

    @Test
    void testUserRoleValueOf() {
        assertEquals(UserRole.USER, UserRole.valueOf("USER"));
        assertEquals(UserRole.ADMIN, UserRole.valueOf("ADMIN"));
    }
}