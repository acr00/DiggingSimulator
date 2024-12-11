package dev.acr.diggingsimulator.Model;

import dev.acr.diggingsimulator.Model.Enums.Coleccion;
import dev.acr.diggingsimulator.Model.Enums.Rareza;
import jakarta.persistence.*;

@Entity
@Table(name = "tesoros")
public class Tesoro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "rareza", nullable = false)
    private Rareza rareza;

    @Column(name = "estado")
    private float estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "coleccion", nullable = false)
    private Coleccion coleccion;

    public Tesoro() {}

    public Tesoro(String nombre, Rareza rareza, float estado, Coleccion coleccion) {
        this.nombre = nombre;
        this.rareza = rareza;
        this.estado = estado;
        this.coleccion = coleccion;
    }

    public float calcularValor() {
        float baseValue = rareza.getValorBase();
        float estadoModificado = estado / 100;
        return baseValue * estadoModificado * coleccion.getMultiplicadorValor();
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

    public Rareza getRareza() {
        return rareza;
    }

    public void setRareza(Rareza rareza) {
        this.rareza = rareza;
    }

    public float getEstado() {
        return estado;
    }

    public void setEstado(float estado) {
        this.estado = estado;
    }

    public Coleccion getColeccion() {
        return coleccion;
    }

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;
    }
}
