package dev.acr.diggingsimulator.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tiendas")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tiendaId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tiendaId")
    private List<Consumible> inventarioConsumibles;

    public Tienda() {
    }

    public Tienda(List<Consumible> inventarioConsumibles) {
        this.inventarioConsumibles = inventarioConsumibles;
    }

    public Long getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Long tiendaId) {
        this.tiendaId = tiendaId;
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
        if (baul.getCapacidadConsumibles() < 100 && baul.getCapacidadTesoros() < 200) {
            baul.mejorarCapacidad();
            return true;
        }
        return false;
    }

    public float venderTesoro(Tesoro tesoro) {
        if (tesoro != null) {
            return tesoro.calcularValor(); 
        }
        return 0.0f; 
    }
}

