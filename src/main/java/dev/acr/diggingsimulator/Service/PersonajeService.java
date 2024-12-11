package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Personaje;
import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import dev.acr.diggingsimulator.Repository.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Personaje crearPersonaje(Personaje personaje, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!personajeRepository.findByUsuario(usuario).isEmpty()) {
                throw new RuntimeException("El usuario ya tiene un personaje.");
        }

        personaje.setUsuario(usuario);
        return personajeRepository.save(personaje);
    }

    public Personaje moverPersonaje(Long personajeId, int deltaX, int deltaY) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));

        personaje.mover(deltaX, deltaY);
        return personajeRepository.save(personaje);
    }

    public Baul.ResultadoAgregar agregarObjetoABaul(Long personajeId, Object objeto) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));

        return personaje.agregarObjetoABaul(objeto);
    }

    @Transactional
    public List<Object> excavar(Long personajeId, int energiaGastada) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));

        if (personaje.getEnergia() < energiaGastada) {
            throw new RuntimeException("Energía insuficiente para excavar");
        }

        List<Object> objetosEncontrados = new ExcavacionService(personajeRepository, null, null).excavar(personajeId, energiaGastada);

        personaje.setEnergia(personaje.getEnergia() - energiaGastada);
        personajeRepository.save(personaje);

        return objetosEncontrados;
    }

    public List<Personaje> obtenerPersonajesDeUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
       
        List<Personaje> personajes = personajeRepository.findByUsuario(usuario);
        if (personajes.isEmpty()) {
            throw new RuntimeException("No se encontraron personajes para este usuario");
        }

        return personajes;
    }

    @Transactional
    public Personaje ganarExperiencia(Long personajeId, int cantidad) {
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));
        
        personaje.setExperiencia(personaje.getExperiencia() + cantidad);
        return personajeRepository.save(personaje);
    }

    @Transactional
    public Personaje restaurarEnergia(Long personajeId) {
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));
        
        personaje.setEnergia(100);
        return personajeRepository.save(personaje);
    }

    public Optional<Personaje> encontrarPersonajePorNombre(String nombre, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return personajeRepository.findByNombreAndUsuario(nombre, usuario);
    }

    public List<Personaje> obtenerPersonajesDeAltoNivel(int nivelMinimo) {
        return personajeRepository.findByNivelGreaterThan(nivelMinimo);
    }
}