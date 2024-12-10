package dev.acr.diggingsimulator.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        when(userRepository.save(any(User.class))).thenReturn(user);

        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(UserRole.USER, registeredUser.getRole());
        assertNotNull(registeredUser.getCreatedAt());

        verify(passwordEncoder).encode(passwordCaptor.capture());
        assertEquals("password123", passwordCaptor.getValue());

        verify(userRepository).save(user);
    }

    @Test
    void testRegisterUser_DuplicateEmail() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(UserService.EmailAlreadyExistsException.class, () -> userService.registerUser(user));
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
    void testLogin_UpdatesLastLogin() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setLastLogin(null);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawPassword", user.getPassword())).thenReturn(true);

        Optional<User> loggedUser = userService.login("testUser", "rawPassword");

        assertTrue(loggedUser.isPresent());
        assertNotNull(user.getLastLogin());
        verify(userRepository).save(user);
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

    @Test
    void testRegisterUser_AssignsRole() {
        User user = new User();
        user.setUsername("newUser");
        user.setEmail("new@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertEquals(UserRole.USER, registeredUser.getRole());
    }

    @Test
    void testLogin_IncorrectPassword() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        Optional<User> loggedUser = userService.login("testUser", "wrongPassword");

        assertTrue(loggedUser.isEmpty());
    }

    @Test
    void testRegisterUser_DuplicateUsername() {
        User user = new User();
        user.setUsername("existingUser");
        user.setEmail("new@example.com");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(new User()));

        assertThrows(UserService.UsernameAlreadyExistsException.class, () -> userService.registerUser(user));
    }

}
