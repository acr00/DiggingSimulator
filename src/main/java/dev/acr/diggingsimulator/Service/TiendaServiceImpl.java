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

    @Override
    public Optional<Tienda> obtenerTiendaPorId(Long id) {
        return tiendaRepository.findById(id);
    }

    @Override
    public float venderTesoro(Long tiendaId, Tesoro tesoro) {
        Optional<Tienda> tiendaOptional = tiendaRepository.findById(tiendaId);
        if (tiendaOptional.isPresent()) {
            return tiendaOptional.get().venderTesoro(tesoro);
        }
        throw new RuntimeException("Tienda no encontrada con id: " + tiendaId);
    }

    @Override
    public boolean comprarMejora(Long tiendaId, Baul baul) {
        Optional<Tienda> tiendaOptional = tiendaRepository.findById(tiendaId);
        if (tiendaOptional.isPresent()) {
            return tiendaOptional.get().comprarMejora(baul);
        }
        throw new RuntimeException("Tienda no encontrada con id: " + tiendaId);
    }
}

