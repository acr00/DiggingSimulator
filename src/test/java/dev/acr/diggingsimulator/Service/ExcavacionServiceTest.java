package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Personaje;
import dev.acr.diggingsimulator.Repository.PersonajeRepository;
import dev.acr.diggingsimulator.Repository.TesoroRepository;
import dev.acr.diggingsimulator.Service.ExcavacionService.InsufficientEnergyException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExcavacionServiceTest {

    @Mock
    private PersonajeRepository personajeRepository;

    @Mock
    private TesoroRepository tesoroRepository;

    @InjectMocks
    private ExcavacionService excavacionService;

    private Personaje personaje;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personaje = new Personaje();
        personaje.setPersonajeId(1L);
        personaje.setEnergia(100);
        personaje.setExperiencia(0);
    }

    @Test
    void testExcavarConEnergiaSuficiente() {
        int energiaGastada = 10;
        when(personajeRepository.findById(1L)).thenReturn(Optional.of(personaje));

        List<Object> objetosEncontrados = excavacionService.excavar(1L, energiaGastada);

        assertNotNull(objetosEncontrados);
        assertTrue(objetosEncontrados.size() <= energiaGastada);
        assertEquals(100 - energiaGastada, personaje.getEnergia());
        assertEquals(energiaGastada + objetosEncontrados.size() * 10, personaje.getExperiencia());
        verify(personajeRepository, times(1)).save(personaje);
    }

    @Test
    void testExcavarConEnergiaInsuficiente() {
        int energiaGastada = 200;
        when(personajeRepository.findById(1L)).thenReturn(Optional.of(personaje));

        InsufficientEnergyException exception = assertThrows(InsufficientEnergyException.class, () -> {
            excavacionService.excavar(1L, energiaGastada);
        });

        assertEquals("Energía insuficiente para excavar", exception.getMessage());
        verify(personajeRepository, times(0)).save(personaje);
    }

    @Test
    void testExcavarCuandoPersonajeNoExiste() {
        when(personajeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            excavacionService.excavar(1L, 10);
        });

        assertEquals("Personaje no encontrado con id: 1", exception.getMessage());
    }

    @Test
    void testGenerarTesorosEncontrados() {
        List<Object> tesoros = excavacionService.generarTesorosEncontrados(5);

        assertNotNull(tesoros);
        assertTrue(tesoros.size() <= 5);
    }

}

