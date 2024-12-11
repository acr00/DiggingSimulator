package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Tesoro;

import java.util.Optional;

public interface TesoroService {
    Tesoro crearTesoro(Tesoro tesoro);
    Optional<Tesoro> obtenerTesoroPorId(Long id);
    Tesoro actualizarTesoro(Tesoro tesoro);
    void eliminarTesoro(Long id);
}
