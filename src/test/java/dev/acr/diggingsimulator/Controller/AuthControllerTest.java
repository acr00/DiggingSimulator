package dev.acr.diggingsimulator.Controller;

import dev.acr.diggingsimulator.Config.LoginRequest;
import dev.acr.diggingsimulator.Config.RegisterRequest;
import dev.acr.diggingsimulator.Service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new LoginRequest("user", "password");
        registerRequest = new RegisterRequest("user@email.test", "username", "!password");
        response = new MockHttpServletResponse();
    }

    @Test
    void testLogin() {
        when(authService.login(loginRequest)).thenReturn("Login successful");

        ResponseEntity<String> result = authController.login(loginRequest, response);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Login successful", result.getBody());
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    void testRegister() {
        when(authService.login(any(LoginRequest.class))).thenReturn("Login successful");

        ResponseEntity<String> result = authController.register(registerRequest, response);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Login successful", result.getBody());
        verify(authService, times(1)).register(registerRequest);
        verify(authService, times(1)).login(any(LoginRequest.class));
    }
}
