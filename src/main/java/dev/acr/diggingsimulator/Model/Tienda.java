package dev.acr.diggingsimulator.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tiendas")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tienda_id")
    private List<Consumible> inventarioConsumibles;

    public Tienda() {
    }

    public Tienda(List<Consumible> inventarioConsumibles) {
        this.inventarioConsumibles = inventarioConsumibles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Consumible> getInventarioConsumibles() {
        return inventarioConsumibles;
    }

    public void setInventarioConsumibles(List<Consumible> inventarioConsumibles) {
        this.inventarioConsumibles = inventarioConsumibles;
    }

    public float venderObjeto(Object objeto) {
        if (objeto instanceof Consumible consumible) {
            inventarioConsumibles.remove(consumible);
            return consumible.getDuracion() * 2.5f;
        }
        return 0.0f;
    }

    public boolean comprarMejora(Baul baul) {
        if (baul.getCapacidadConsumibles() < 50 && baul.getCapacidadTesoros() < 20) {
            baul.mejorarCapacidad();
            return true;
        }
        return false;
    }

    public float venderTesoro(Tesoro tesoro) {
        if (tesoro != null) {
            return tesoro.calcularValor(); // El método calcularValor ya existe en Tesoro
        }
        return 0.0f; // Si el tesoro es nulo, no devuelve monedas
    }
}

