package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Tienda;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TiendaServiceImpl implements TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    private Tienda obtenerTiendaExistente(Long tiendaId) {
        return tiendaRepository.findById(tiendaId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con id: " + tiendaId));
    }

    @Override
    public Optional<Tienda> obtenerTiendaPorId(Long id) {
        return tiendaRepository.findById(id);
    }

    @Override
    public float venderTesoro(Long tiendaId, Tesoro tesoro) {
        Tienda tienda = obtenerTiendaExistente(tiendaId);
        return tienda.venderTesoro(tesoro);
    }

    @Override
    public boolean comprarMejora(Long tiendaId, Baul baul) {
        Tienda tienda = obtenerTiendaExistente(tiendaId);
        if (tienda.comprarMejora(baul)) {
            return true;
        }
        throw new RuntimeException("No se pudo comprar la mejora, los límites ya fueron alcanzados.");
    }
}

