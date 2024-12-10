package dev.acr.diggingsimulator.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setRole(UserRole.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now().minusDays(1));

        
        userDto = new UserDto(user);
    }

    @Test
    void testUserDtoConstructor_UserEntity() {
        
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getRole(), userDto.getRole());
        assertEquals(user.getCreatedAt(), userDto.getCreatedAt());
        assertEquals(user.getLastLogin(), userDto.getLastLogin());
    }

    @Test
    void testUserDtoConstructor_Empty() {
        
        UserDto emptyDto = new UserDto();
        assertNull(emptyDto.getId());
        assertNull(emptyDto.getEmail());
        assertNull(emptyDto.getUsername());
        assertNull(emptyDto.getRole());
        assertNull(emptyDto.getCreatedAt());
        assertNull(emptyDto.getLastLogin());
    }

    @Test
    void testSettersAndGetters() {
       
        userDto.setId(2L);
        userDto.setEmail("newemail@example.com");
        userDto.setUsername("newusername");
        userDto.setRole(UserRole.ADMIN);
        userDto.setCreatedAt(LocalDateTime.now().minusHours(1));
        userDto.setLastLogin(LocalDateTime.now());

        assertEquals(2L, userDto.getId());
        assertEquals("newemail@example.com", userDto.getEmail());
        assertEquals("newusername", userDto.getUsername());
        assertEquals(UserRole.ADMIN, userDto.getRole());
        assertNotNull(userDto.getCreatedAt());
        assertNotNull(userDto.getLastLogin());
    }

    @Test
    void testToEntity() {
        
        User convertedUser = userDto.toEntity();

        assertEquals(userDto.getId(), convertedUser.getId());
        assertEquals(userDto.getEmail(), convertedUser.getEmail());
        assertEquals(userDto.getUsername(), convertedUser.getUsername());
        assertEquals(userDto.getRole(), convertedUser.getRole());
        assertEquals(userDto.getCreatedAt(), convertedUser.getCreatedAt());
        assertEquals(userDto.getLastLogin(), convertedUser.getLastLogin());
    }

    @Test
    void testUserDtoConstructor_NullValues() {

        User userWithNulls = new User();
        userWithNulls.setId(2L);
        userWithNulls.setEmail(null); 
        userWithNulls.setUsername(null); 
        userWithNulls.setRole(null); 
        userWithNulls.setCreatedAt(null); 
        userWithNulls.setLastLogin(null); 

    
        UserDto userDtoWithNulls = new UserDto(userWithNulls);

    
        assertNull(userDtoWithNulls.getEmail());
        assertNull(userDtoWithNulls.getUsername());
        assertNull(userDtoWithNulls.getRole());
        assertNull(userDtoWithNulls.getCreatedAt());
        assertNull(userDtoWithNulls.getLastLogin());
    }

    @Test
    void testToEntity_WithNullValues() {
    
        UserDto userDtoWithNulls = new UserDto();
        userDtoWithNulls.setId(3L);
        userDtoWithNulls.setEmail(null);  
        userDtoWithNulls.setUsername(null);  
        userDtoWithNulls.setRole(null);  
        userDtoWithNulls.setCreatedAt(null);  
        userDtoWithNulls.setLastLogin(null);  

    
        User userFromDto = userDtoWithNulls.toEntity();

    
        assertNull(userFromDto.getEmail());
        assertNull(userFromDto.getUsername());
        assertNull(userFromDto.getRole());
        assertNull(userFromDto.getCreatedAt());
        assertNull(userFromDto.getLastLogin());
    }

    @Test
    void testToString_WithNullValues() {
    
    UserDto userDtoWithNulls = new UserDto();
    userDtoWithNulls.setId(4L);
    userDtoWithNulls.setEmail(null);  
    userDtoWithNulls.setUsername(null); 
    userDtoWithNulls.setRole(null);  
    userDtoWithNulls.setCreatedAt(null);  
    userDtoWithNulls.setLastLogin(null);  

    
    String expectedToString = "UserDto{" +
            "id=" + userDtoWithNulls.getId() +
            ", email='null'" +  
            ", username='null'" +
            ", role=null" +
            ", createdAt=null" +
            ", lastLogin=null" +
            '}';

    assertEquals(expectedToString, userDtoWithNulls.toString());
    }

    @Test
    void testToString() {

        String expectedToString = "UserDto{" +
                "id=" + userDto.getId() +
                ", email='" + userDto.getEmail() + '\'' +
                ", username='" + userDto.getUsername() + '\'' +
                ", role=" + userDto.getRole() +
                ", createdAt=" + userDto.getCreatedAt() +
                ", lastLogin=" + userDto.getLastLogin() +
                '}';

        assertEquals(expectedToString, userDto.toString());
    }

    @Test
    void testEqualsAndHashCode() {
    UserDto userDto1 = new UserDto(1L, "email1@example.com", "username1", UserRole.USER, LocalDateTime.now(), LocalDateTime.now());
    UserDto userDto2 = new UserDto(1L, "email1@example.com", "username1", UserRole.USER, LocalDateTime.now(), LocalDateTime.now());

    
    assertEquals(userDto1, userDto2);
    assertEquals(userDto1.hashCode(), userDto2.hashCode());

    }

}