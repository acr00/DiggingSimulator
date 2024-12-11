package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.TesoroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TesoroServiceImpl implements TesoroService {

    private final TesoroRepository tesoroRepository;

    public TesoroServiceImpl(TesoroRepository tesoroRepository) {
        this.tesoroRepository = tesoroRepository;
    }

    @Override
    public Tesoro crearTesoro(Tesoro tesoro) {
        return tesoroRepository.save(tesoro);
    }

    @Override
    public Optional<Tesoro> obtenerTesoroPorId(Long id) {
        Optional<Tesoro> tesoroOptional = tesoroRepository.findById(id);
        if (tesoroOptional.isEmpty()) {
            throw new EntityNotFoundException("Tesoro no encontrado con id: " + id);
        }
        return tesoroOptional;
    }

    @Override
    public Tesoro actualizarTesoro(Tesoro tesoro) {
        Optional<Tesoro> existenteOptional = obtenerTesoroPorId(tesoro.getId());
        if (existenteOptional.isEmpty()) {
            throw new EntityNotFoundException("Tesoro no encontrado con id: " + tesoro.getId());
        }
        return tesoroRepository.save(tesoro);
    }

    @Override
    public void eliminarTesoro(Long id) {
        Optional<Tesoro> existenteOptional = obtenerTesoroPorId(id);
        if (existenteOptional.isEmpty()) {
            throw new EntityNotFoundException("Tesoro no encontrado con id: " + id);
        }
        tesoroRepository.delete(existenteOptional.get());
    }
}