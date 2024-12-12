package dev.acr.diggingsimulator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.acr.diggingsimulator.Config.LoginRequest;
import dev.acr.diggingsimulator.Config.RegisterRequest;
import dev.acr.diggingsimulator.Service.AuthService;

@RestController
@RequestMapping("/ds/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    
    

}