package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Model.Personaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonajeRepositoryTest {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Personaje personaje1;
    private Personaje personaje2;

    @BeforeEach
    void setUp() {
  
        usuario = new Usuario();
        usuario.setUsername("testUser");
        usuario.setPassword("password");
        usuarioRepository.save(usuario);

        personaje1 = new Personaje();
        personaje1.setNombre("Personaje1");
        personaje1.setUsuario(usuario);
        personaje1.setNivel(10);
        personaje1.setExperiencia(200);
        personajeRepository.save(personaje1);

        personaje2 = new Personaje();
        personaje2.setNombre("Personaje2");
        personaje2.setUsuario(usuario);
        personaje2.setNivel(20);
        personaje2.setExperiencia(500);
        personajeRepository.save(personaje2);
    }

    @Test
    void testFindByUsuario() {
        List<Personaje> personajes = personajeRepository.findByUsuario(usuario);
        assertNotNull(personajes);
        assertEquals(2, personajes.size());
        assertTrue(personajes.contains(personaje1));
        assertTrue(personajes.contains(personaje2));
    }

    @Test
    void testFindByNombreAndUsuario() {
        Optional<Personaje> personaje = personajeRepository.findByNombreAndUsuario("Personaje1", usuario);
        assertTrue(personaje.isPresent());
        assertEquals(personaje1.getNombre(), personaje.get().getNombre());
    }

    @Test
    void testCountByUsuario() {
        int count = personajeRepository.countByUsuario(usuario);
        assertEquals(2, count);
    }

    @Test
    void testFindByNivelGreaterThan() {
        List<Personaje> personajes = personajeRepository.findByNivelGreaterThan(15);
        assertNotNull(personajes);
        assertEquals(1, personajes.size());
        assertEquals(personaje2, personajes.get(0));
    }

    @Test
    void testFindByOrderByExperienciaDesc() {
        List<Personaje> personajes = personajeRepository.findByOrderByExperienciaDesc();
        assertNotNull(personajes);
        assertEquals(2, personajes.size());
        assertEquals(personaje2, personajes.get(0)); 
        assertEquals(personaje1, personajes.get(1)); 
    }

}

