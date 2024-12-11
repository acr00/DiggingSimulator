package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Consumible;

import java.util.Optional;

public interface ConsumibleService {
    Consumible crearConsumible(Consumible consumible);
    Optional<Consumible> obtenerConsumiblePorId(Long id);
    Consumible actualizarConsumible(Consumible consumible);
    void eliminarConsumible(Long id);
}