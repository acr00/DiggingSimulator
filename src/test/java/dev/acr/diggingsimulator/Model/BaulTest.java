package dev.acr.diggingsimulator.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaulTest {

    private Baul baul;
    private Tesoro tesoro;
    private Personaje personaje;

    @BeforeEach
    void setUp() {
        baul = new Baul();  
        tesoro = new Tesoro(); 
        personaje = new Personaje(); 
    }

    @Test
    void testAgregarTesoro() {
        assertEquals(Baul.CapacidadStatus.OK, baul.agregarTesoro(tesoro));
        assertEquals(1, baul.getNumeroTesoros());
    }

    @Test
    void testAgregarTesoroCuandoEstaLleno() {
        for (int i = 0; i < 20; i++) {
            baul.agregarTesoro(tesoro);
        }
        assertEquals(Baul.CapacidadStatus.CAPACIDAD_LLENA, baul.agregarTesoro(tesoro));
        assertEquals(20, baul.getNumeroTesoros());
    }

    @Test
    void testMejorarCapacidadConSuficientesMonedas() {
        float monedasAntes = personaje.getMonedas();
        baul.mejorarCapacidad(baul.getBaulId(), personaje);
        assertEquals(monedasAntes - 100, personaje.getMonedas());
        assertEquals(30, baul.getCapacidadTesoros());
    }

    @Test
    void testMejorarCapacidadConPocasMonedas() {
        personaje.setMonedas(50);  
        baul.mejorarCapacidad(baul.getBaulId(), personaje);
        assertEquals(50, personaje.getMonedas());  
        assertEquals(20, baul.getCapacidadTesoros()); 
    }

    @Test
    void testEstaLleno() {
        for (int i = 0; i < 20; i++) {
            baul.agregarTesoro(tesoro);
        }
        assertTrue(baul.estaLleno());

        baul.agregarTesoro(tesoro);  
        assertTrue(baul.estaLleno());
    }

    @Test
    void testCapacidadStatus() {
        assertEquals(Baul.CapacidadStatus.OK, baul.agregarTesoro(tesoro));
        baul.agregarTesoro(tesoro);  
        assertEquals(Baul.CapacidadStatus.CAPACIDAD_LLENA, baul.agregarTesoro(tesoro));
    }

    @Test
    void testAgregarTesoroNulo() {
        assertThrows(IllegalArgumentException.class, () -> baul.agregarTesoro(null));
    }

    @Test
    void testAgregarTesoroCuandoEstaLlenoYNoSePuedeMejorar() {
        for (int i = 0; i < 20; i++) {
            baul.agregarTesoro(tesoro);
        }
        baul.mejorarCapacidad(baul.getBaulId(), personaje);
        assertEquals(30, baul.getCapacidadTesoros());
        for (int i = 0; i < 10; i++) {
            baul.agregarTesoro(tesoro);
        }
        assertEquals(Baul.CapacidadStatus.CAPACIDAD_LLENA, baul.agregarTesoro(tesoro));
    }

    @Test
    void testMejorarCapacidadSinMonedas() {
        personaje.setMonedas(0); 
        baul.mejorarCapacidad(baul.getBaulId(), personaje);
        assertEquals(0, personaje.getMonedas()); 
        assertEquals(20, baul.getCapacidadTesoros());
    }

    @Test
    void testGetNumeroTesoros() {
        assertEquals(0, baul.getNumeroTesoros());
        baul.agregarTesoro(tesoro);
        assertEquals(1, baul.getNumeroTesoros());
    }
}

