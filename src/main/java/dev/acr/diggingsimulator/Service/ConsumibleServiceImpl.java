package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Consumible;
import dev.acr.diggingsimulator.Repository.ConsumibleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsumibleServiceImpl implements ConsumibleService {

    private final ConsumibleRepository consumibleRepository;

    public ConsumibleServiceImpl(ConsumibleRepository consumibleRepository) {
        this.consumibleRepository = consumibleRepository;
    }

    @Override
    public Consumible crearConsumible(Consumible consumible) {
        return consumibleRepository.save(consumible);
    }

    @Override
    public Optional<Consumible> obtenerConsumiblePorId(Long consumibleId) {
        Optional<Consumible> consumibleOptional = consumibleRepository.findById(consumibleId);
        if (consumibleOptional.isEmpty()) {
            throw new EntityNotFoundException("Consumible no encontrado con id: " + consumibleId);
        }
        return consumibleOptional;
    }

    @Override
public Consumible actualizarConsumible(Consumible consumible) {
    Optional<Consumible> existenteOptional = obtenerConsumiblePorId(consumible.getConsumibleId());
    if (existenteOptional.isEmpty()) {
        throw new EntityNotFoundException("Consumible no encontrado con id: " + consumible.getConsumibleId());
    }
    return consumibleRepository.save(consumible);
    }

    @Override
public void eliminarConsumible(Long consumibleId) {
    Optional<Consumible> existenteOptional = obtenerConsumiblePorId(consumibleId);
    if (existenteOptional.isEmpty()) {
        throw new EntityNotFoundException("Consumible no encontrado con id: " + consumibleId);
    }
    consumibleRepository.delete(existenteOptional.get());
    }

}
