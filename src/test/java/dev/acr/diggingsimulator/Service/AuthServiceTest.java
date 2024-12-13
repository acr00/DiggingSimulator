package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Config.JwtUtils;
import dev.acr.diggingsimulator.Config.LoginRequest;
import dev.acr.diggingsimulator.Config.RegisterRequest;
import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerRequest = new RegisterRequest("user1", "password", "user1@example.com");
        loginRequest = new LoginRequest("user1", "password");
        usuario = Usuario.builder()
                .username("user1")
                .password("encodedPassword")
                .email("user1@example.com")
                .role(Usuario.Role.ROLE_USER)
                .build();
    }

    @Test
    void testRegister() {
        when(passwordEncoder.encode(registerRequest.password())).thenReturn("encodedPassword");

        authService.register(registerRequest);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testLogin() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Simula autenticación exitosa.
        when(jwtUtils.generateToken(loginRequest.username())).thenReturn("mockedJwtToken");

        String token = authService.login(loginRequest);

        assertNotNull(token);
        assertEquals("mockedJwtToken", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateToken(loginRequest.username());
    }

    @Test
    void testRegisterWithExistingUser() {
        when(usuarioRepository.findByUsername(registerRequest.username())).thenReturn(java.util.Optional.of(usuario));

        authService.register(registerRequest);

        verify(usuarioRepository, times(1)).save(any(Usuario.class)); // Usuario se guarda siempre, aunque exista uno con el mismo nombre
    }
}
