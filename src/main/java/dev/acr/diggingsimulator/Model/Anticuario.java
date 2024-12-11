package dev.acr.diggingsimulator.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "anticuarios")
public class Anticuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anticuarioId;

    public Anticuario() {
    }

    public Tesoro mejorarEstado(Tesoro tesoro, float monedas) {
        if (monedas >= 10.0f) {
        tesoro.setEstado(Math.min(tesoro.getEstado() + 10.0f, 100.0f));
        }
        return tesoro;
    }
}

