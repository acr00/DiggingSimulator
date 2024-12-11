package dev.acr.diggingsimulator.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "baules")
public class Baul {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacidad_consumibles")
    private int capacidadConsumibles;

    @Column(name = "capacidad_tesoros")
    private int capacidadTesoros;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "baul_id")
    private List<Consumible> consumibles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "baul_id")
    private List<Tesoro> tesoros = new ArrayList<>();

    public Baul() {
        this.capacidadConsumibles = 10;
        this.capacidadTesoros = 20;
    }

    public Baul(int capacidadConsumibles, int capacidadTesoros) {
        this.capacidadConsumibles = capacidadConsumibles;
        this.capacidadTesoros = capacidadTesoros;
    }

    public enum ResultadoAgregar {
        OK,
        CAPACIDAD_LLENA
    }

    public ResultadoAgregar agregarConsumible(Consumible consumible) {
        if (consumibles.size() < capacidadConsumibles) {
            consumibles.add(consumible);
            return ResultadoAgregar.OK;
        }
        return ResultadoAgregar.CAPACIDAD_LLENA;
    }

    public ResultadoAgregar agregarTesoro(Tesoro tesoro) {
        if (tesoros.size() < capacidadTesoros) {
            tesoros.add(tesoro);
            return ResultadoAgregar.OK;
        }
        return ResultadoAgregar.CAPACIDAD_LLENA;
    }

    public void mejorarCapacidad() {
        this.capacidadConsumibles += 10;
        this.capacidadTesoros += 5;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacidadConsumibles() {
        return capacidadConsumibles;
    }

    public void setCapacidadConsumibles(int capacidadConsumibles) {
        this.capacidadConsumibles = capacidadConsumibles;
    }

    public int getCapacidadTesoros() {
        return capacidadTesoros;
    }

    public void setCapacidadTesoros(int capacidadTesoros) {
        this.capacidadTesoros = capacidadTesoros;
    }

    public List<Consumible> getConsumibles() {
        return consumibles;
    }

    public void setConsumibles(List<Consumible> consumibles) {
        this.consumibles = consumibles;
    }

    public List<Tesoro> getTesoros() {
        return tesoros;
    }

    public void setTesoros(List<Tesoro> tesoros) {
        this.tesoros = tesoros;
    }
    public int getNumeroConsumibles() {
        return consumibles.size();
    }

    public int getNumeroTesoros() {
        return tesoros.size();
    }

    public boolean estaLleno() {
        return consumibles.size() >= capacidadConsumibles && 
               tesoros.size() >= capacidadTesoros;
    }
}
