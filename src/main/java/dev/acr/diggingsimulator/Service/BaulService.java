package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Consumible;
import dev.acr.diggingsimulator.Model.Tesoro;

import java.util.Optional;

public interface BaulService {
    Baul crearBaul(Baul baul);
    Optional<Baul> obtenerBaulPorId(Long id);
    Baul actualizarBaul(Baul baul);
    void eliminarBaul(Long id);

    boolean agregarConsumible(Long baulId, Consumible consumible);
    boolean agregarTesoro(Long baulId, Tesoro tesoro);
}


