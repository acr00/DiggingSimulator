package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Personaje;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Model.Consumible;
import dev.acr.diggingsimulator.Model.Enums.Rareza;
import dev.acr.diggingsimulator.Model.Enums.TipoEfecto;
import dev.acr.diggingsimulator.Repository.PersonajeRepository;
import dev.acr.diggingsimulator.Repository.TesoroRepository;
import dev.acr.diggingsimulator.Repository.ConsumibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ExcavacionService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private TesoroRepository tesoroRepository;

    @Autowired
    private ConsumibleRepository consumibleRepository;

    private Random random = new Random();

    @Transactional
    public List<Object> excavar(Long personajeId, int energiaGastada) {
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));

        if (personaje.getEnergia() < energiaGastada) {
            throw new RuntimeException("Energía insuficiente para excavar");
        }

        personaje.setEnergia(personaje.getEnergia() - energiaGastada);
        List<Object> objetosEncontrados = new ArrayList<>();

        for (int i = 0; i < energiaGastada; i++) {
            if (random.nextFloat() < 0.1) { // 10% de probabilidad de encontrar algo
                if (random.nextBoolean()) {
                    objetosEncontrados.add(generarTesoro());
                } else {
                    objetosEncontrados.add(generarConsumible());
                }
            }
        }

        int experienciaGanada = energiaGastada + (objetosEncontrados.size() * 10);
        personaje.ganarExperiencia(experienciaGanada);

        personajeRepository.save(personaje);
        return objetosEncontrados;
    }

    private Tesoro generarTesoro() {
        Rareza rareza = Rareza.values()[random.nextInt(Rareza.values().length)];
        return new Tesoro("Tesoro encontrado", rareza, 100, null); // El estado inicial es 100
    }

    private Consumible generarConsumible() {
        TipoEfecto efecto = TipoEfecto.values()[random.nextInt(TipoEfecto.values().length)];
        return new Consumible("Consumible encontrado", efecto, random.nextInt(5) + 1);
    }
}
