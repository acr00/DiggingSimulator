package dev.acr.diggingsimulator.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "baules")
public class Baul {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baulId;

    @Column(name = "capacidad_tesoros")
    private int capacidadTesoros;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "baul_Id")
    private List<Tesoro> tesoros = new ArrayList<>();

    public Baul() {
        this.capacidadTesoros = 20;
    }

    public Baul(int capacidadTesoros) {
        this.capacidadTesoros = capacidadTesoros;
    }

    public enum CapacidadStatus {
        OK,
        CAPACIDAD_LLENA
    }

    public CapacidadStatus agregarTesoro(Tesoro tesoro) {
        if (tesoros.size() < capacidadTesoros) {
            tesoros.add(tesoro);
            return CapacidadStatus.OK;
        }
        return CapacidadStatus.CAPACIDAD_LLENA;
    }

    public void mejorarCapacidad() {
        this.capacidadTesoros += 5;
    }
    public Long getBaulId() {
        return baulId;
    }

    public void setBaulId(Long baulId) {
        this.baulId = baulId;
    }

    public int getCapacidadTesoros() {
        return capacidadTesoros;
    }

    public void setCapacidadTesoros(int capacidadTesoros) {
        this.capacidadTesoros = capacidadTesoros;
    }

    public List<Tesoro> getTesoros() {
        return tesoros;
    }

    public void setTesoros(List<Tesoro> tesoros) {
        this.tesoros = tesoros;
    }

    public int getNumeroTesoros() {
        return tesoros.size();
    }

    public boolean estaLleno() {
        return tesoros.size() >= capacidadTesoros;
    }
}
