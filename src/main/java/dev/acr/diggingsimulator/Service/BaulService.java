package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Baul.CapacidadStatus;
import dev.acr.diggingsimulator.Model.Tesoro;

import java.util.Optional;

public interface BaulService {
    Baul crearBaul(Baul baul);
    Optional<Baul> obtenerBaulPorId(Long baulId);
    Baul actualizarBaul(Baul baulId);
    void eliminarBaul(Long baulId);

    CapacidadStatus agregarTesoro(Long baulId, Tesoro tesoro);
}


