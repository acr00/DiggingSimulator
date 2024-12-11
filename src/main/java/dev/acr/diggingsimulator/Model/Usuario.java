package dev.acr.diggingsimulator.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import dev.acr.diggingsimulator.Model.Enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "El email es obligatorio")
    @Column(unique = true)
    private String email;

    @PositiveOrZero(message = "La moneda no puede ser negativa")
    private float moneda = 0f;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.USER; 

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    
    public boolean esAdmin() {
        return this.role == UserRole.ADMIN;
    }

    public void cambiarRol(UserRole nuevoRol) {
        if (this.esAdmin()) {
            this.role = nuevoRol;
        } else {
            throw new SecurityException("Solo un administrador puede cambiar roles");
        }
    }
    public boolean registrarse() {
        return this.username != null && !this.username.isEmpty() 
               && this.password != null && this.password.length() >= 8;
    }
    public void actualizarMoneda(float cantidad) {
        if (this.moneda + cantidad >= 0) {
            this.moneda += cantidad;
        }
    }
}