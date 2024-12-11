package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Anticuario;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.AnticuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnticuarioServiceImpl implements AnticuarioService {

    private final AnticuarioRepository anticuarioRepository;

    @Autowired
    public AnticuarioServiceImpl(AnticuarioRepository anticuarioRepository) {
        this.anticuarioRepository = anticuarioRepository;
    }

    @Override
    public Tesoro mejorarEstado(Long anticuarioId, Tesoro tesoro, float monedas) {
        Anticuario anticuario = anticuarioRepository.findById(anticuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Anticuario no encontrado con id: " + anticuarioId));

        return anticuario.mejorarEstado(tesoro, monedas);
    }
}