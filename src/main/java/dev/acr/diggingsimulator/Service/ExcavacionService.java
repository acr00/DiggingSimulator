package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Personaje;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Model.Enums.Coleccion;
import dev.acr.diggingsimulator.Model.Enums.Rareza;
import dev.acr.diggingsimulator.Repository.PersonajeRepository;
import dev.acr.diggingsimulator.Repository.TesoroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ExcavacionService {

    public class InsufficientEnergyException extends RuntimeException {
    public InsufficientEnergyException(String message) {
        super(message);
    }
}

    private final PersonajeRepository personajeRepository;
    private final TesoroRepository tesoroRepository;
    private final Random random = new Random();

    public ExcavacionService(PersonajeRepository personajeRepository, TesoroRepository tesoroRepository) {
        this.personajeRepository = personajeRepository;
        this.tesoroRepository = tesoroRepository;
    }

    @Transactional
    public List<Object> excavar(Long personajeId, int energiaGastada) {
        Personaje personaje = obtenerPersonajeExistente(personajeId);

        if (personaje.getEnergia() < energiaGastada) {
            throw new InsufficientEnergyException("Energía insuficiente para excavar");
        }

        personaje.setEnergia(personaje.getEnergia() - energiaGastada);
        List<Object> objetosEncontrados = generarTesorosEncontrados(energiaGastada);

        int experienciaGanada = energiaGastada + (objetosEncontrados.size() * 10);
        personaje.setExperiencia(personaje.getExperiencia() + experienciaGanada);

        personajeRepository.save(personaje);
        return objetosEncontrados;
    }

    private Personaje obtenerPersonajeExistente(Long personajeId) {
        return personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));
    }

    List<Object> generarTesorosEncontrados(int energiaGastada) {
        List<Object> objetosEncontrados = new ArrayList<>();
        for (int i = 0; i < energiaGastada; i++) {
            if (random.nextFloat() < 0.1) {
                objetosEncontrados.add(generarTesoroAleatorio());
            }
        }
        return objetosEncontrados;
    }

    private <Optional>Tesoro generarTesoroAleatorio() {

            Tesoro tesoro = generarTesoro();
            tesoroRepository.save(tesoro);
            return tesoro;
    }

    private Tesoro generarTesoro() {
        Rareza rareza = Rareza.values()[random.nextInt(Rareza.values().length)];
        Coleccion coleccion = Coleccion.values()[random.nextInt(Coleccion.values().length)];
        return new Tesoro("Tesoro encontrado", rareza, 100.0f, coleccion);
    }
    
}

