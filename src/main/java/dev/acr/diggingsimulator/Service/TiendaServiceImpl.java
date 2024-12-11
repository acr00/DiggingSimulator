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
        return tiendaRepository.findById(tiendaId)
                .orElseThrow(() -> new EntityNotFoundException("Tienda no encontrada con id: " + tiendaId));
    }

    @Override
    public Optional<Tienda> obtenerTiendaPorId(Long tiendaId) {
        return tiendaRepository.findById(tiendaId);
    }

    @Override
    public float venderTesoro(Long tiendaId, Tesoro tesoro) {
        Tienda tienda = obtenerTiendaExistente(tiendaId);
        return tienda.venderTesoro(tesoro);
    }

    @Override
    public boolean comprarMejora(Long tiendaId, Baul baul) {
        Tienda tienda = obtenerTiendaExistente(tiendaId);
        return tienda.comprarMejora(baul);
    }

}

