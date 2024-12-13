package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Personaje;
import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import dev.acr.diggingsimulator.Repository.BaulRepository;
import dev.acr.diggingsimulator.Repository.PersonajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonajeService {

    public class InsufficientEnergyException extends RuntimeException {
        public InsufficientEnergyException(String message) {
            super(message);
        }
    }

    private final PersonajeRepository personajeRepository;
    private final UsuarioRepository usuarioRepository;

    public PersonajeService(PersonajeRepository personajeRepository, UsuarioRepository usuarioRepository, BaulRepository baulRepository) {
        this.personajeRepository = personajeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Personaje crearPersonaje(Personaje personaje, Long usuarioId) {
        Usuario usuario = obtenerUsuarioExistente(usuarioId);
        validarPersonajeExistente(usuario, personaje);
        personaje.setUsuario(usuario);
        return personajeRepository.save(personaje);
    }

    private Usuario obtenerUsuarioExistente(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + usuarioId));
    }

    private void validarPersonajeExistente(Usuario usuario, Personaje personaje) {
        if (!personajeRepository.findByUsuario(usuario).isEmpty()) {
            throw new IllegalArgumentException("El usuario ya tiene un personaje.");
        }
    }

    public void moverPersonaje(Long personajeId, int deltaX, int deltaY) {
        Personaje personaje = personajeRepository.findById(personajeId).orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado"));
        personaje.mover(deltaX, deltaY);
        personajeRepository.save(personaje);
    }

    public Baul.CapacidadStatus agregarObjetoABaul(Long personajeId, Object objeto) {
        Personaje personaje = obtenerPersonajeExistente(personajeId);
        return personaje.agregarObjetoABaul(objeto);
    }

    @Transactional
    public List<Object> excavar(Long personajeId, int energiaGastada) {
        Personaje personaje = obtenerPersonajeExistente(personajeId);
        if (personaje.getEnergia() < energiaGastada) {
            throw new InsufficientEnergyException("Energía insuficiente para excavar");
        }

        List<Object> objetosEncontrados = new ExcavacionService(personajeRepository, null).excavar(personajeId, energiaGastada);
        personaje.setEnergia(personaje.getEnergia() - energiaGastada);
        personajeRepository.save(personaje);

        return objetosEncontrados;
    }

    public List<Personaje> obtenerPersonajesDeUsuario(Long usuarioId) {
        Usuario usuario = obtenerUsuarioExistente(usuarioId);
        List<Personaje> personajes = personajeRepository.findByUsuario(usuario);
        if (personajes.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron personajes para este usuario");
        }
        return personajes;
    }

    @Transactional
    public Personaje ganarExperiencia(Long personajeId, int cantidad) {
        Personaje personaje = obtenerPersonajeExistente(personajeId);
        personaje.setExperiencia(personaje.getExperiencia() + cantidad);
        return personajeRepository.save(personaje);
    }

    @Transactional
    public Personaje restaurarEnergia(Long personajeId) {
        Personaje personaje = obtenerPersonajeExistente(personajeId);
        personaje.setEnergia(100);
        return personajeRepository.save(personaje);
    }

    public Optional<Personaje> encontrarPersonajePorNombre(String nombre, Long usuarioId) {
        Usuario usuario = obtenerUsuarioExistente(usuarioId);
        return personajeRepository.findByNombreAndUsuario(nombre, usuario);
    }

    public List<Personaje> obtenerPersonajesDeAltoNivel(int nivelMinimo) {
        return personajeRepository.findByNivelGreaterThan(nivelMinimo);
    }

    private Personaje obtenerPersonajeExistente(Long personajeId) {
        return personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));
    }

}