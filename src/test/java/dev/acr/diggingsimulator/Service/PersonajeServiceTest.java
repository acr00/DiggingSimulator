package dev.acr.diggingsimulator.Service;


import dev.acr.diggingsimulator.Model.Personaje;
import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.PersonajeRepository;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class PersonajeServiceTest {

    @Mock
    private PersonajeRepository personajeRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PersonajeService personajeService;

    private Personaje personaje;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setUsuarioId(1L);
        personaje = new Personaje();
        personaje.setPersonajeId(1L);
        personaje.setNombre("Personaje 1");
        personaje.setUsuario(usuario);
    }

    @Test
    void testCrearPersonajeExitoso() {
        // Configuramos el mock para que devuelva un usuario válido
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(personajeRepository.save(personaje)).thenReturn(personaje);

        // Llamamos al método
        Personaje result = personajeService.crearPersonaje(personaje, 1L);

        // Verificamos el resultado
        assertNotNull(result);
        assertEquals(1L, result.getPersonajeId());
        assertEquals("Personaje 1", result.getNombre());
    }

    @Test
    void testFindPersonajeByIdExistente() {
        
        when(personajeRepository.findById(1L)).thenReturn(Optional.of(personaje));

        
        Personaje result = personajeService.findPersonajeById(1L);

        
        assertNotNull(result);
        assertEquals(1L, result.getPersonajeId());
        assertEquals("Personaje 1", result.getNombre());
    }

    @Test
    void testFindPersonajeByIdNoExistente() {

        when(personajeRepository.findById(1L)).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personajeService.findPersonajeById(1L);
        });


        assertEquals("Personaje no encontrado con id: 1", exception.getMessage());
    }

    @Test
    void testMoverPersonajeExitoso() {

        when(personajeRepository.findById(1L)).thenReturn(Optional.of(personaje));

        personajeService.moverPersonaje(1L, 10, 20);


        assertEquals(11, personaje.getPosicionX());
        assertEquals(21, personaje.getPosicionY());
        verify(personajeRepository, times(1)).save(personaje); 
    }

    @Test
    void testMoverPersonajeNoExistente() {

        when(personajeRepository.findById(1L)).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            personajeService.moverPersonaje(1L, 10, 20);
        });


        assertEquals("Personaje no encontrado", exception.getMessage());
    }
}

