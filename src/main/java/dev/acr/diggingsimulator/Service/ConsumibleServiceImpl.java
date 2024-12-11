package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Consumible;
import dev.acr.diggingsimulator.Repository.ConsumibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsumibleServiceImpl implements ConsumibleService {

    @Autowired
    private ConsumibleRepository consumibleRepository;

    @Override
    public Consumible crearConsumible(Consumible consumible) {
        return consumibleRepository.save(consumible);
    }

    @Override
    public Optional<Consumible> obtenerConsumiblePorId(Long id) {
        return consumibleRepository.findById(id);
    }

    @Override
    public Consumible actualizarConsumible(Consumible consumible) {
        if (consumibleRepository.existsById(consumible.getId())) {
            return consumibleRepository.save(consumible);
        } else {
            throw new RuntimeException("Consumible no encontrado con id: " + consumible.getId());
        }
    }

    @Override
    public void eliminarConsumible(Long id) {
        if (consumibleRepository.existsById(id)) {
            consumibleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Consumible no encontrado con id: " + id);
        }
    }
}
