package dev.acr.diggingsimulator.Users;

import java.time.LocalDateTime;
import java.io.Serializable;

public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String username;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    
    public UserDto() {}

    
    public UserDto(Long id, String email, String username, UserRole role, 
                   LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    
    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.lastLogin = user.getLastLogin();
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }

    
    public dev.acr.diggingsimulator.Users.User toEntity() {
        dev.acr.diggingsimulator.Users.User user = new dev.acr.diggingsimulator.Users.User();
        user.setId(this.id);
        user.setEmail(this.email);
        user.setUsername(this.username);
        user.setRole(this.role);
        user.setCreatedAt(this.createdAt);
        user.setLastLogin(this.lastLogin);
        return user;
    }
}