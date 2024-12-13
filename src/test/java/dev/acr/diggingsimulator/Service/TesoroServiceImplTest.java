package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Model.Enums.Coleccion;
import dev.acr.diggingsimulator.Model.Enums.Rareza;
import dev.acr.diggingsimulator.Repository.TesoroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class TesoroServiceImplTest {

    @Mock
    private TesoroRepository tesoroRepository;

    @InjectMocks
    private TesoroServiceImpl tesoroService;

    private Tesoro tesoro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tesoro = new Tesoro("Tesoro de prueba", Rareza.RARO, 100.0f, Coleccion.VIKINGA);
    }

    @Test
    void testCrearTesoro() {
        when(tesoroRepository.save(any(Tesoro.class))).thenReturn(tesoro);

        Tesoro nuevoTesoro = tesoroService.crearTesoro(tesoro);

        assertNotNull(nuevoTesoro);
        assertEquals("Tesoro de prueba", nuevoTesoro.getNombre());
        verify(tesoroRepository, times(1)).save(tesoro);
    }

    @Test
    void testObtenerTesoroPorIdExistente() {
        when(tesoroRepository.findById(1L)).thenReturn(Optional.of(tesoro));

        Optional<Tesoro> tesoroEncontrado = tesoroService.obtenerTesoroPorId(1L);

        assertTrue(tesoroEncontrado.isPresent());
        assertEquals(tesoro.getNombre(), tesoroEncontrado.get().getNombre());
        verify(tesoroRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerTesoroPorIdNoExistente() {
        when(tesoroRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            tesoroService.obtenerTesoroPorId(1L);
        });

        assertEquals("Tesoro no encontrado con id: 1", exception.getMessage());
        verify(tesoroRepository, times(1)).findById(1L);
    }

    @Test
    void testActualizarTesoroExistente() {
        when(tesoroRepository.findById(1L)).thenReturn(Optional.of(tesoro));
        when(tesoroRepository.save(any(Tesoro.class))).thenReturn(tesoro);

        tesoro.setNombre("Nuevo Tesoro");
        Tesoro tesoroActualizado = tesoroService.actualizarTesoro(tesoro);

        assertNotNull(tesoroActualizado);
        assertEquals("Nuevo Tesoro", tesoroActualizado.getNombre());
        verify(tesoroRepository, times(1)).findById(1L);
        verify(tesoroRepository, times(1)).save(tesoro);
    }

    @Test
    void testActualizarTesoroNoExistente() {
        when(tesoroRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            tesoroService.actualizarTesoro(tesoro);
        });

        assertEquals("Tesoro no encontrado con id: 1", exception.getMessage());
        verify(tesoroRepository, times(1)).findById(1L);
        verify(tesoroRepository, times(0)).save(tesoro);
    }

    @Test
    void testEliminarTesoroExistente() {
        when(tesoroRepository.findById(1L)).thenReturn(Optional.of(tesoro));

        tesoroService.eliminarTesoro(1L);

        verify(tesoroRepository, times(1)).delete(tesoro);
    }

    @Test
    void testEliminarTesoroNoExistente() {
        when(tesoroRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            tesoroService.eliminarTesoro(1L);
        });

        assertEquals("Tesoro no encontrado con id: 1", exception.getMessage());
        verify(tesoroRepository, times(1)).findById(1L);
        verify(tesoroRepository, times(0)).delete(tesoro);
    }
}
