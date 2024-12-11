package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Anticuario;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.AnticuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnticuarioServiceImpl implements AnticuarioService {

    @Autowired
    private AnticuarioRepository anticuarioRepository;

    @Override
    public Tesoro mejorarEstado(Long anticuarioId, Tesoro tesoro, float monedas) {
        Optional<Anticuario> anticuarioOptional = anticuarioRepository.findById(anticuarioId);
        if (anticuarioOptional.isPresent()) {
            return anticuarioOptional.get().mejorarEstado(tesoro, monedas);
        }
        throw new RuntimeException("Anticuario no encontrado con id: " + anticuarioId);
    }
}