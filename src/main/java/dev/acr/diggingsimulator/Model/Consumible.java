package dev.acr.diggingsimulator.Model;

import dev.acr.diggingsimulator.Model.Enums.TipoEfecto;
import jakarta.persistence.*;

@Entity
@Table(name = "consumibles")
public class Consumible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "efecto", nullable = false)
    private TipoEfecto efecto;

    @Column(name = "duracion")
    private int duracion;

    
    public Consumible() {}

    
    public Consumible(String nombre, TipoEfecto efecto, int duracion) {
        this.nombre = nombre;
        this.efecto = efecto;
        this.duracion = duracion;
    }

    
    public void aplicarEfecto() {
        // Lógica de aplicación del efecto
        System.out.println("Aplicando efecto: " + efecto + " durante " + duracion + " turnos.");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoEfecto getEfecto() {
        return efecto;
    }

    public void setEfecto(TipoEfecto efecto) {
        this.efecto = efecto;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
