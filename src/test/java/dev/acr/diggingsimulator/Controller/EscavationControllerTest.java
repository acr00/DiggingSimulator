package dev.acr.diggingsimulator.Controller;

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

class ExcavacionControllerTest {

    @Mock
    private PersonajeService personajeService;

    @InjectMocks
    private ExcavacionController excavacionController;

    private Long personajeId;
    private int energiaGastada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personajeId = 1L;
        energiaGastada = 50;
    }

    @Test
    void testExcavarExitoso() {
        List<Object> objetosEncontrados = Collections.singletonList(new Object());
        when(personajeService.excavar(personajeId, energiaGastada)).thenReturn(objetosEncontrados);

        ResponseEntity<List<Object>> result = excavacionController.excavar(personajeId, energiaGastada);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(objetosEncontrados, result.getBody());
        verify(personajeService, times(1)).excavar(personajeId, energiaGastada);
    }

    @Test
    void testExcavarConError() {
        when(personajeService.excavar(personajeId, energiaGastada)).thenThrow(new RuntimeException("Error en la excavación"));

        ResponseEntity<List<Object>> result = excavacionController.excavar(personajeId, energiaGastada);

        assertEquals(400, result.getStatusCode().value());
        assertNull(result.getBody());
        verify(personajeService, times(1)).excavar(personajeId, energiaGastada);
    }
}
