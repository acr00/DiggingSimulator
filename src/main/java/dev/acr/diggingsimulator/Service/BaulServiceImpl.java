package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Model.Consumible;
import dev.acr.diggingsimulator.Repository.BaulRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaulServiceImpl implements BaulService {

    private final BaulRepository baulRepository;

    public BaulServiceImpl(BaulRepository baulRepository) {
        this.baulRepository = baulRepository;
    }

private Baul obtenerBaulExistente(Long baulId) {
    Optional<Baul> baulOptional = baulRepository.findById(baulId);
    if (baulOptional.isEmpty()) {
        throw new EntityNotFoundException("Baúl no encontrado con id: " + baulId);
    }
    return baulOptional.get();
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
        obtenerBaulExistente(baul.getId());
        return baulRepository.save(baul);
    }

    @Override
    public void eliminarBaul(Long id) {
        Baul baul = obtenerBaulExistente(id);
        baulRepository.delete(baul);
    }

    @Override
    public Baul.ResultadoAgregar agregarConsumible(Long baulId, Consumible consumible) {
        return agregarObjetoAlBaul(baulId, consumible);
    }

    @Override
    public Baul.ResultadoAgregar agregarTesoro(Long baulId, Tesoro tesoro) {
        return agregarObjetoAlBaul(baulId, tesoro);
    }

    private Baul.ResultadoAgregar agregarObjetoAlBaul(Long baulId, Object objeto) {
        Baul baul = obtenerBaulExistente(baulId);
        Baul.ResultadoAgregar resultado;

        if (objeto instanceof Consumible consumible) {
            resultado = baul.agregarConsumible(consumible);
        } else if (objeto instanceof Tesoro tesoro) {
            resultado = baul.agregarTesoro(tesoro);
        } else {
            throw new IllegalArgumentException("El objeto debe ser un Consumible o Tesoro.");
        }

        if (resultado == Baul.ResultadoAgregar.OK) {
            baulRepository.save(baul);
        }

        return resultado;
    }
}