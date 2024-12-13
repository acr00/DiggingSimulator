package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Repository.BaulRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaulServiceImplTest {

    @Mock
    private BaulRepository baulRepository;

    @InjectMocks
    private BaulServiceImpl baulService;

    private Baul baul;
    private Tesoro tesoro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        baul = new Baul();
        baul.setBaulId(1L);
        tesoro = new Tesoro("Tesoro1", null, 100f, null);
    }

    @Test
    void testCrearBaul() {
        when(baulRepository.save(baul)).thenReturn(baul);

        Baul result = baulService.crearBaul(baul);

        assertNotNull(result);
        assertEquals(baul.getBaulId(), result.getBaulId());
        verify(baulRepository, times(1)).save(baul);
    }

    @Test
    void testObtenerBaulPorIdExistente() {
        when(baulRepository.findById(1L)).thenReturn(Optional.of(baul));

        Optional<Baul> result = baulService.obtenerBaulPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(baul.getBaulId(), result.get().getBaulId());
        verify(baulRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerBaulPorIdNoExistente() {
        when(baulRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Baul> result = baulService.obtenerBaulPorId(1L);

        assertFalse(result.isPresent());
        verify(baulRepository, times(1)).findById(1L);
    }

    @Test
    void testActualizarBaul() {
        when(baulRepository.findById(1L)).thenReturn(Optional.of(baul));
        when(baulRepository.save(baul)).thenReturn(baul);

        Baul result = baulService.actualizarBaul(baul);

        assertNotNull(result);
        assertEquals(baul.getBaulId(), result.getBaulId());
        verify(baulRepository, times(1)).findById(1L);
        verify(baulRepository, times(1)).save(baul);
    }

    @Test
    void testActualizarBaulNoExistente() {
        when(baulRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            baulService.actualizarBaul(baul);
        });
        verify(baulRepository, times(1)).findById(1L);
    }

    @Test
    void testEliminarBaul() {
        when(baulRepository.findById(1L)).thenReturn(Optional.of(baul));

        baulService.eliminarBaul(1L);

        verify(baulRepository, times(1)).findById(1L);
        verify(baulRepository, times(1)).delete(baul);
    }

    @Test
    void testEliminarBaulNoExistente() {
        when(baulRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            baulService.eliminarBaul(1L);
        });
        verify(baulRepository, times(1)).findById(1L);
    }

    @Test
    void testAgregarTesoro() {
        Baul.CapacidadStatus capacidadStatus = Baul.CapacidadStatus.OK;
        when(baulRepository.findById(1L)).thenReturn(Optional.of(baul));
        when(baul.agregarTesoro(any(Tesoro.class))).thenReturn(capacidadStatus);

        Baul.CapacidadStatus result = baulService.agregarTesoro(1L, tesoro);

        assertEquals(capacidadStatus, result);
        verify(baulRepository, times(1)).findById(1L);
        verify(baul, times(1)).agregarTesoro(tesoro);
    }

    @Test
    void testAgregarTesoroBaulNoExistente() {
        when(baulRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            baulService.agregarTesoro(1L, tesoro);
        });
        verify(baulRepository, times(1)).findById(1L);
    }
}

