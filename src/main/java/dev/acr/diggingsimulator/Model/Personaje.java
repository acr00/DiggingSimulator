package dev.acr.diggingsimulator.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personajes")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personajeId;

    @NotBlank(message = "El nombre del personaje es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Min(value = 0, message = "La posición X no puede ser negativa")
    private int posicionX = 0;

    @Min(value = 0, message = "La posición Y no puede ser negativa")
    private int posicionY = 0;

    @NotNull(message = "El usuario propietario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @Min(value = 1, message = "El nivel mínimo es 1")
    private int nivel = 1;

    @PositiveOrZero(message = "La experiencia no puede ser negativa")
    private int experiencia = 0;

    @PositiveOrZero(message = "La moneda no puede ser negativa")
    private float moneda = 0f;

    @PositiveOrZero(message = "La energía no puede ser negativa")
    private int energia = 100;

    @PositiveOrZero(message = "La capacidad de carga no puede ser negativa")
    private int capacidadCarga = 50;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "baulId", referencedColumnName = "baulId")
    private Baul baul;

    @Column(name = "ultima_regeneracion_energia")
    private LocalDateTime ultimaRegeneracionEnergia;


    public void mover(int deltaX, int deltaY) {
        
        this.posicionX = Math.max(0, Math.min(1000, this.posicionX + deltaX));
        this.posicionY = Math.max(0, Math.min(1000, this.posicionY + deltaY));


        
        int costoMovimiento = Math.abs(deltaX) + Math.abs(deltaY);
        this.energia = Math.max(0, this.energia - costoMovimiento);
    }


    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad;
        while (this.experiencia >= 100 * this.nivel) {
            subirNivel();
        }
    }

    private void subirNivel() {
        this.nivel++;
        this.experiencia -= 100 * (this.nivel - 1);
        this.energia = Math.min(100 + (this.nivel * 10), 200);
    }


    public void restaurarEnergia() {
        this.energia = Math.min(100 + (this.nivel * 10), 200);
    }

    public void regenerarEnergia() {
        LocalDateTime ahora = LocalDateTime.now();
        long minutosTranscurridos = java.time.Duration.between(ultimaRegeneracionEnergia, ahora).toMinutes();
        int energiaRegenerada = (int) (minutosTranscurridos * 0.5); 
        this.energia = Math.min(this.energia + energiaRegenerada, 100 + (this.nivel * 10));
        this.ultimaRegeneracionEnergia = ahora;
    }
    
    public Baul.CapacidadStatus agregarObjetoABaul(Object objeto) {
        if (objeto instanceof Consumible consumible) {
            return baul.agregarConsumible(consumible);
        } else if (objeto instanceof Tesoro tesoro) {
            return baul.agregarTesoro(tesoro);
        }
        throw new IllegalArgumentException("El objeto no es ni un Consumible ni un Tesoro.");
    }

    public void actualizarMoneda(float cantidad) {
        if (this.moneda + cantidad >= 0) {
            this.moneda += cantidad;
        }
    }

    public int getPosicionX() {
        return posicionX;
    }


    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }


    public int getPosicionY() {
        return posicionY;
    }


    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }


    public int getEnergia() {
        return energia;
    }


    public void setEnergia(int energia) {
        this.energia = energia;
    }


    public int getExperiencia() {
        return experiencia;
    }


    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }


    public int getNivel() {
        return nivel;
    }


    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public float getMoneda() {
        return moneda;
    }

    public void setMoneda(float moneda) {
        this.moneda = moneda;
    }
}

