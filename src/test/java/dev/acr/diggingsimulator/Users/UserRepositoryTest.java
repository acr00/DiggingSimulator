package dev.acr.diggingsimulator.Users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import dev.acr.diggingsimulator.Model.User;
import dev.acr.diggingsimulator.Model.Enums.UserRole;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByUsername_Exists() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("testpassword");
        user.setRole(UserRole.USER);
        entityManager.persist(user);
        entityManager.flush();
    
        Optional<User> found = userRepository.findByUsername("testuser");
    
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
    }
    

    @Test
    void testFindByUsername_NotExists() {
        
        Optional<User> found = userRepository.findByUsername("nonexistent");

        
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindByEmail_Exists() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("testpassword"); 
        user.setRole(UserRole.USER); 
        entityManager.persist(user);
        entityManager.flush();
    
        Optional<User> found = userRepository.findByEmail("test@example.com");
    
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void testFindByEmail_NotExists() {
        
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");

        
        assertTrue(found.isEmpty());
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("testpassword"); 
        user.setRole(UserRole.USER); 
    
        User savedUser = userRepository.save(user);
    
        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
    }
}