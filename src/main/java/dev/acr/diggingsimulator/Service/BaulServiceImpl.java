package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.BaulRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaulServiceImpl implements BaulService {

    private final BaulRepository baulRepository;

    public BaulServiceImpl(BaulRepository baulRepository, PersonajeService personajeRepository) {
        this.baulRepository = baulRepository;
    }

    private Baul obtenerBaulExistente(Long baulId) {
        return baulRepository.findById(baulId)
                .orElseThrow(() -> new EntityNotFoundException("Baúl no encontrado con id: " + baulId));
    }
    @Override
    public Baul crearBaul(Baul baul) {
        return baulRepository.save(baul);
    }

    @Override
    public Optional<Baul> obtenerBaulPorId(Long id) {
        return baulRepository.findById(id);
    }

    @Override
    public Baul actualizarBaul(Baul baul) {
        obtenerBaulExistente(baul.getBaulId());
        return baulRepository.save(baul);
    }

    @Override
    public void eliminarBaul(Long id) {
        Baul baul = obtenerBaulExistente(id);
        baulRepository.delete(baul);
    }

    @Override
    public Baul.CapacidadStatus agregarTesoro(Long baulId, Tesoro tesoro) {
        return agregarTesoro(baulId, tesoro);
    }

}
