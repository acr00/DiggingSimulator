package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import dev.acr.diggingsimulator.Service.UsuarioService.UserAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Usuario admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario(null, "username1", "password123", "user@example.com", Usuario.Role.ROLE_USER);
        admin = new Usuario(null, "admin", "adminpass", "admin@example.com", Usuario.Role.ROLE_ADMIN);
    }

    @Test
    void testRegistrarUsuarioExitoso() {
        when(usuarioRepository.existsByUsername(usuario.getUsername())).thenReturn(false);
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(usuario.getPassword())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);

        assertNotNull(usuarioRegistrado);
        assertEquals("encodedPassword", usuarioRegistrado.getPassword());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testRegistrarUsuarioConUsernameExistente() {
        when(usuarioRepository.existsByUsername(usuario.getUsername())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            usuarioService.registrarUsuario(usuario);
        });

        assertEquals("El nombre de usuario ya existe", exception.getMessage());
        verify(usuarioRepository, times(0)).save(usuario);
    }

    @Test
    void testRegistrarUsuarioConEmailExistente() {
        when(usuarioRepository.existsByUsername(usuario.getUsername())).thenReturn(false);
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            usuarioService.registrarUsuario(usuario);
        });

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(usuarioRepository, times(0)).save(usuario);
    }

    @Test
    void testCambiarRolUsuarioExitoso() {
        Long usuarioId = 1L;
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioConNuevoRol = usuarioService.cambiarRol(usuarioId, Usuario.Role.ROLE_ADMIN, admin);

        assertNotNull(usuarioConNuevoRol);
        assertEquals(Usuario.Role.ROLE_ADMIN, usuarioConNuevoRol.getRole());
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCambiarRolUsuarioNoExistente() {
        Long usuarioId = 1L;
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.cambiarRol(usuarioId, Usuario.Role.ROLE_ADMIN, admin);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    void testObtenerTodosLosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario, admin));

        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios(admin);

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }
}