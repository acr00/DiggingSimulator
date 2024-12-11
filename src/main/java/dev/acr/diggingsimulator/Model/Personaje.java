package dev.acr.diggingsimulator.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personajes")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del personaje es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Min(value = 0, message = "La posición X no puede ser negativa")
    private int posicionX = 0;

    @Min(value = 0, message = "La posición Y no puede ser negativa")
    private int posicionY = 0;

    @NotNull(message = "El usuario propietario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Estadísticas del personaje
    @Min(value = 1, message = "El nivel mínimo es 1")
    private int nivel = 1;

    @PositiveOrZero(message = "La experiencia no puede ser negativa")
    private int experiencia = 0;

    @PositiveOrZero(message = "La energía no puede ser negativa")
    private int energia = 100;

    @PositiveOrZero(message = "La capacidad de carga no puede ser negativa")
    private int capacidadCarga = 50;

    // Métodos de movimiento
    public void mover(int deltaX, int deltaY) {
        // Validar movimiento dentro de límites (por ejemplo, un mapa de 1000x1000)
        this.posicionX = Math.max(0, Math.min(1000, this.posicionX + deltaX));
        this.posicionY = Math.max(0, Math.min(1000, this.posicionY + deltaY));
        
        // Reducir energía por movimiento
        int costoMovimiento = Math.abs(deltaX) + Math.abs(deltaY);
        this.energia = Math.max(0, this.energia - costoMovimiento);
    }

    // Método para ganar experiencia
    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad;
        
        // Sistema de subida de nivel simple
        if (this.experiencia >= 100 * this.nivel) {
            subirNivel();
        }
    }

    // Método para subir de nivel
    private void subirNivel() {
        this.nivel++;
        this.experiencia -= 100 * (this.nivel - 1);
        
    // Mejoras al subir de nivel
        this.energia = Math.min(100 + (this.nivel * 10), 200);
        this.capacidadCarga += 10;
    }

    // Método para restaurar energía
    public void restaurarEnergia() {
        this.energia = Math.min(100 + (this.nivel * 10), 200);
    }
}