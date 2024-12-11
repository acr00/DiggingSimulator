package dev.acr.diggingsimulator.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.acr.diggingsimulator.Model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userService.registerUser(user)).thenReturn(user);

        
        ResponseEntity<?> response = userController.registerUser(user);

        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).registerUser(user);
    }

    @Test
    void testRegisterUser_Failure() {
        
        User user = new User();
        user.setUsername("testUser");

        when(userService.registerUser(user)).thenThrow(new RuntimeException("User already exists"));

        
        ResponseEntity<?> response = userController.registerUser(user);

        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    void testLogin_Success() {
        
        UserController.LoginRequest loginRequest = new UserController.LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("password123");

        User user = new User();
        user.setUsername("testUser");

        when(userService.login("testUser", "password123")).thenReturn(Optional.of(user));

        
        ResponseEntity<?> response = userController.login(loginRequest);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).login("testUser", "password123");
    }

    @Test
    void testLogin_Failure() {
        
        UserController.LoginRequest loginRequest = new UserController.LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("wrongPassword");

        when(userService.login("testUser", "wrongPassword")).thenReturn(Optional.empty());

       
        ResponseEntity<?> response = userController.login(loginRequest);

        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(userService).login("testUser", "wrongPassword");
    }
}