package dev.acr.diggingsimulator.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        
        User user = new User();
        user.setUsername("newUser");
        user.setEmail("new@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        
        User registeredUser = userService.registerUser(user);

        
        assertNotNull(registeredUser);
        assertEquals(UserRole.USER, registeredUser.getRole());
        assertNotNull(registeredUser.getCreatedAt());
        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void testRegisterUser_DuplicateEmail() {
        
        User user = new User();
        user.setEmail("existing@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new User()));

        
        assertThrows(RuntimeException.class, () -> userService.registerUser(user));
    }

    @Test
    void testLogin_Success() {
        
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawPassword", user.getPassword())).thenReturn(true);

        
        Optional<User> loggedUser = userService.login("testUser", "rawPassword");

        
        assertTrue(loggedUser.isPresent());
        assertNotNull(user.getLastLogin());
        verify(userRepository).save(user);
    }

    @Test
    void testLogin_Failure() {
        
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        
        Optional<User> loggedUser = userService.login("testUser", "password");

        
        assertTrue(loggedUser.isEmpty());
    }

    @Test
    void testFindById() {
        
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        
        Optional<User> foundUser = userService.findById(1L);

        
        assertTrue(foundUser.isPresent());
        assertEquals(1L, foundUser.get().getId());
    }

    @Test
    void testFindByUsername() {
        
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        
        Optional<User> foundUser = userService.findByUsername("testUser");

        
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }
}