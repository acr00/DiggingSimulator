package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;

import java.util.Optional;

public interface BaulService {
    Baul crearBaul(Baul baul);
    Optional<Baul> obtenerBaulPorId(Long id);
    Baul actualizarBaul(Baul baul);
    void eliminarBaul(Long id);
}


