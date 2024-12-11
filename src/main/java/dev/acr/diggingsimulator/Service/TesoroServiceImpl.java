package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.TesoroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TesoroServiceImpl implements TesoroService {

    @Autowired
    private TesoroRepository tesoroRepository;

    @Override
    public Tesoro crearTesoro(Tesoro tesoro) {
        return tesoroRepository.save(tesoro);
    }

    @Override
    public Optional<Tesoro> obtenerTesoroPorId(Long id) {
        return tesoroRepository.findById(id);
    }

    @Override
    public Tesoro actualizarTesoro(Tesoro tesoro) {
        if (tesoroRepository.existsById(tesoro.getId())) {
            return tesoroRepository.save(tesoro);
        } else {
            throw new RuntimeException("Tesoro no encontrado con id: " + tesoro.getId());
        }
    }

    @Override
    public void eliminarTesoro(Long id) {
        if (tesoroRepository.existsById(id)) {
            tesoroRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tesoro no encontrado con id: " + id);
        }
    }
}