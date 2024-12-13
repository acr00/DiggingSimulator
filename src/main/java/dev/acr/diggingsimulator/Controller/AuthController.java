package dev.acr.diggingsimulator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.acr.diggingsimulator.Config.LoginRequest;
import dev.acr.diggingsimulator.Config.RegisterRequest;
import dev.acr.diggingsimulator.Service.AuthService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ds/auth")

public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@ModelAttribute LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute RegisterRequest registerRequest, HttpServletResponse response) {
        authService.register(registerRequest);
        return login(new LoginRequest(registerRequest.username(), registerRequest.password()), response);
    }

}
