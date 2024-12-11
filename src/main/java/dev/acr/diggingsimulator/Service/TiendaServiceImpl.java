package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Tienda;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.TiendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TiendaServiceImpl implements TiendaService {

    private final TiendaRepository tiendaRepository;

    public TiendaServiceImpl(TiendaRepository tiendaRepository) {
        this.tiendaRepository = tiendaRepository;
    }

    private Tienda obtenerTiendaExistente(Long tiendaId) {
        Optional<Tienda> tiendaOptional = tiendaRepository.findById(tiendaId);
        if (tiendaOptional.isEmpty()) {
            throw new EntityNotFoundException("Tienda no encontrada con id: " + tiendaId);
        }
        return tiendaOptional.get();
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
        if (!tienda.comprarMejora(baul)) {
            throw new IllegalStateException("No se pudo comprar la mejora, los límites ya fueron alcanzados.");
        }
        return true;
    }
}

