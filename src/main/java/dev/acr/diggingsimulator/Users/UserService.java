package dev.acr.diggingsimulator.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        
        
        if (userRepository.findByEmail(user.getEmail()).isPresent() ||
            userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Email o username ya registrado");
        }

        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        
        user.setRole(UserRole.USER);
        
       
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public Optional<User> login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent() && 
            passwordEncoder.matches(password, userOptional.get().getPassword())) {
            
            User user = userOptional.get();
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            
            return userOptional;
        }
        
        return Optional.empty();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}