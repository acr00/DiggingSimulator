package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.TesoroRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.springframework.stereotype.Service;

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
    public Optional<Tesoro> obtenerTesoroPorId(Long tesoroId) {
        Optional<Tesoro> tesoroOptional = tesoroRepository.findById(tesoroId);
        if (tesoroOptional.isEmpty()) {
            throw new EntityNotFoundException("Tesoro no encontrado con id: " + tesoroId);
        }
        return tesoroOptional;
    }

    @Override
    public Tesoro actualizarTesoro(Tesoro tesoro) {
        obtenerTesoroPorId(tesoro.getTesoroId());
        return tesoroRepository.save(tesoro);
    }
    @Override
public void eliminarTesoro(Long tesoroId) {
    Optional<Tesoro> existenteOptional = obtenerTesoroPorId(tesoroId);
    if (existenteOptional.isEmpty()) {
        throw new EntityNotFoundException("Tesoro no encontrado con id: " + tesoroId);
    }
    tesoroRepository.delete(existenteOptional.get());
    }

}