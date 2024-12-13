package dev.acr.diggingsimulator.Controller;

import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Service.BaulService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaulControllerTest {

    @Mock
    private BaulService baulService;

    @InjectMocks
    private BaulController baulController;

    private Tesoro tesoro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tesoro = new Tesoro("Oro", null, 80, null);
    }

    @Test
    void testAgregarTesoro() {
        Long baulId = 1L;

        doNothing().when(baulService).agregarTesoro(baulId, tesoro);

        ResponseEntity<String> result = baulController.agregarTesoro(baulId, tesoro);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Tesoro agregado al baúl.", result.getBody());
        verify(baulService, times(1)).agregarTesoro(baulId, tesoro);
    }
}
