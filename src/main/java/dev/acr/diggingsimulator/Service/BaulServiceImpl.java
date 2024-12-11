package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Repository.BaulRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaulServiceImpl implements BaulService {

    @Autowired
    private BaulRepository baulRepository;

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
        if (baulRepository.existsById(baul.getId())) {
            return baulRepository.save(baul);
        } else {
            throw new RuntimeException("Baúl no encontrado con id: " + baul.getId());
        }
    }

    @Override
    public void eliminarBaul(Long id) {
        if (baulRepository.existsById(id)) {
            baulRepository.deleteById(id);
        } else {
            throw new RuntimeException("Baúl no encontrado con id: " + id);
        }
    }
}
