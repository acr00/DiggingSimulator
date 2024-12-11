package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Model.Tienda;

import java.util.Optional;

public interface TiendaService {
    Optional<Tienda> obtenerTiendaPorId(Long id);
    float venderTesoro(Long tiendaId, Tesoro tesoro);
    boolean comprarMejora(Long tiendaId, Baul baul);
}

