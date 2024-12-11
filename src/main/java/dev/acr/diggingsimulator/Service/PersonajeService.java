package dev.acr.diggingsimulator.Service;

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

    @Autowired
    private ExcavacionService excavacionService;

    // Crear un nuevo personaje para un usuario
    @Transactional
    public Personaje crearPersonaje(Personaje personaje, Long usuarioId) {
        // Verificar límite de personajes por usuario (por ejemplo, máximo 3)
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        int contadorPersonajes = personajeRepository.countByUsuario(usuario);
        if (contadorPersonajes >= 3) {
            throw new RuntimeException("Límite máximo de personajes alcanzado");
        }

        personaje.setUsuario(usuario);
        return personajeRepository.save(personaje);
    }

    @Transactional
    public List<Object> excavar(Long personajeId, int energiaGastada) {
        return excavacionService.excavar(personajeId, energiaGastada);
    }

    // Obtener personajes de un usuario
    public List<Personaje> obtenerPersonajesDeUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return personajeRepository.findByUsuario(usuario);
    }

    // Mover personaje
    @Transactional
    public Personaje moverPersonaje(Long personajeId, int deltaX, int deltaY) {
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));
        
        personaje.mover(deltaX, deltaY);
        return personajeRepository.save(personaje);
    }

    // Ganar experiencia
    @Transactional
    public Personaje ganarExperiencia(Long personajeId, int cantidad) {
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));
        
        personaje.ganarExperiencia(cantidad);
        return personajeRepository.save(personaje);
    }

    // Restaurar energía de un personaje
    @Transactional
    public Personaje restaurarEnergia(Long personajeId) {
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));
        
        personaje.restaurarEnergia();
        return personajeRepository.save(personaje);
    }

    // Encontrar personaje por nombre de usuario
    public Optional<Personaje> encontrarPersonajePorNombre(String nombre, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return personajeRepository.findByNombreAndUsuario(nombre, usuario);
    }

    // Obtener personajes de alto nivel
    public List<Personaje> obtenerPersonajesDeAltoNivel(int nivelMinimo) {
        return personajeRepository.findByNivelGreaterThan(nivelMinimo);
    }
}
