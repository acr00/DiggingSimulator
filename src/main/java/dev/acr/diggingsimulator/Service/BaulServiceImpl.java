package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Model.Consumible;
import dev.acr.diggingsimulator.Repository.BaulRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaulServiceImpl implements BaulService {

    @Autowired
    private BaulRepository baulRepository;

    private Baul obtenerBaulExistente(Long baulId) {
        return baulRepository.findById(baulId)
                .orElseThrow(() -> new RuntimeException("Baúl no encontrado con id: " + baulId));
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
        obtenerBaulExistente(id);
        baulRepository.deleteById(id);
    }

    @Override
    public Baul.ResultadoAgregar agregarConsumible(Long baulId, Consumible consumible) {
        Baul baul = obtenerBaulExistente(baulId);
        Baul.ResultadoAgregar resultado = baul.agregarConsumible(consumible);
        if (resultado == Baul.ResultadoAgregar.OK) {
            baulRepository.save(baul);
        }
        return resultado;
    }

    @Override
    public Baul.ResultadoAgregar agregarTesoro(Long baulId, Tesoro tesoro) {
        Baul baul = obtenerBaulExistente(baulId);
        Baul.ResultadoAgregar resultado = baul.agregarTesoro(tesoro);
        if (resultado == Baul.ResultadoAgregar.OK) {
            baulRepository.save(baul);
        }
        return resultado;
    }
}