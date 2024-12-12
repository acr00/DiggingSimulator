package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Config.JwtUtils;
import dev.acr.diggingsimulator.Config.LoginRequest;
import dev.acr.diggingsimulator.Config.RegisterRequest;
import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void register(RegisterRequest registerRequest) {

        Usuario usuario = Usuario.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .email(registerRequest.email())
                .role(Usuario.Role.USER)
                .build();
                usuarioRepository.save(usuario);
                
    }

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        return jwtUtils.generateToken(loginRequest.username());
    }

}
