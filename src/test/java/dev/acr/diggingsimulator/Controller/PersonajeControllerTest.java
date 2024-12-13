package dev.acr.diggingsimulator.Controller;

import dev.acr.diggingsimulator.Model.Personaje;
import dev.acr.diggingsimulator.Service.PersonajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonajeControllerTest {

    @Mock
    private PersonajeService personajeService;

    @InjectMocks
    private PersonajeController personajeController;

    private Personaje personaje;
    private Long usuarioId;
    private Long personajeId;
    private int energiaGastada;
    private int deltaX;
    private int deltaY;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personaje = new Personaje();
        usuarioId = 1L;
        personajeId = 1L;
        energiaGastada = 50;
        deltaX = 10;
        deltaY = 15;
    }

    @Test
    void testCrearPersonaje() {
        when(personajeService.crearPersonaje(personaje, usuarioId)).thenReturn(personaje);

        ResponseEntity<?> result = personajeController.crearPersonaje(personaje, usuarioId);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(personaje, result.getBody());
        verify(personajeService, times(1)).crearPersonaje(personaje, usuarioId);
    }

    @Test
    void testExcavarExitoso() {
        List<Object> objetosEncontrados = Collections.singletonList(new Object());
        when(personajeService.excavar(personajeId, energiaGastada)).thenReturn(objetosEncontrados);

        ResponseEntity<?> result = personajeController.excavar(personajeId, energiaGastada);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(objetosEncontrados, result.getBody());
        verify(personajeService, times(1)).excavar(personajeId, energiaGastada);
    }

    @Test
    void testExcavarConError() {
        when(personajeService.excavar(personajeId, energiaGastada)).thenThrow(new RuntimeException("Error en la excavación"));

        ResponseEntity<?> result = personajeController.excavar(personajeId, energiaGastada);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Error en la excavación", result.getBody());
        verify(personajeService, times(1)).excavar(personajeId, energiaGastada);
    }

    @Test
    void testMoverPersonaje() {
        ResponseEntity<String> result = personajeController.moverPersonaje(personajeId, deltaX, deltaY);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Personaje movido con éxito.", result.getBody());
        verify(personajeService, times(1)).moverPersonaje(personajeId, deltaX, deltaY);
    }

    @Test
    void testRegenerarEnergia() {
        ResponseEntity<String> result = personajeController.regenerarEnergia(personajeId);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Energía regenerada con éxito.", result.getBody());
        verify(personajeService, times(1)).restaurarEnergia(personajeId);
    }

    @Test
    void testObtenerPersonajeDeUsuario() {
        List<Personaje> personajes = Collections.singletonList(new Personaje());
        when(personajeService.obtenerPersonajesDeUsuario(usuarioId)).thenReturn(personajes);

        ResponseEntity<?> result = personajeController.obtenerPersonajeDeUsuario(usuarioId);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        verify(personajeService, times(1)).obtenerPersonajesDeUsuario(usuarioId);
    }

    @Test
    void testObtenerPersonajeDeUsuarioConError() {
        when(personajeService.obtenerPersonajesDeUsuario(usuarioId)).thenThrow(new RuntimeException("Error al obtener personajes"));

        ResponseEntity<?> result = personajeController.obtenerPersonajeDeUsuario(usuarioId);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Error al obtener personajes", result.getBody());
        verify(personajeService, times(1)).obtenerPersonajesDeUsuario(usuarioId);
    }

    @Test
    void testObtenerPersonajeDeUsuarioNoEncontrado() {
        when(personajeService.obtenerPersonajesDeUsuario(usuarioId)).thenReturn(Collections.emptyList());

        ResponseEntity<?> result = personajeController.obtenerPersonajeDeUsuario(usuarioId);

        assertEquals(404, result.getStatusCode().value());
        assertNull(result.getBody());
        verify(personajeService, times(1)).obtenerPersonajesDeUsuario(usuarioId);
    }
}

