package dev.acr.diggingsimulator.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class PersonajeTest {

    private Personaje personaje;
    private Tesoro tesoro;

    @BeforeEach
    void setUp() {
        tesoro = new Tesoro();
        personaje = new Personaje();
    }

    @Test
    void testMover() {
        personaje.mover(10, 15);
        assertEquals(11, personaje.getPosicionX());
        assertEquals(16, personaje.getPosicionY());
        assertEquals(85, personaje.getEnergia());
    }

    @Test
    void testMoverLimites() {
        personaje.mover(-2000, 1500);
        assertEquals(0, personaje.getPosicionX());
        assertEquals(1000, personaje.getPosicionY());
    }

    @Test
    void testGanarExperiencia() {
        personaje.ganarExperiencia(200);
        assertTrue(personaje.getNivel() > 1);
        assertTrue(personaje.getExperiencia() < 100);
    }

    @Test
    void testSubirNivel() {
        personaje.ganarExperiencia(200);
        assertEquals(120, personaje.getEnergia());
    }

    @Test
    void testRegenerarEnergia() throws InterruptedException {
        personaje.setUltimaRegeneracionEnergia(LocalDateTime.now().minusMinutes(30));
        personaje.regenerarEnergia();
        assertTrue(personaje.getEnergia() > 100);
    }

    @Test
    void testAgregarObjetoABaul() {
        assertEquals(Baul.CapacidadStatus.OK, personaje.agregarObjetoABaul(tesoro));
        assertEquals(1, personaje.getBaul().getNumeroTesoros());
    }

    @Test
    void testAgregarObjetoIncorrectoABaul() {
        assertThrows(IllegalArgumentException.class, () -> personaje.agregarObjetoABaul("No es un tesoro"));
    }

    @Test
    void testActualizarMonedas() {
        float monedasIniciales = personaje.getMoneda();
        personaje.actualizarMonedas(50);
        assertEquals(monedasIniciales + 50, personaje.getMoneda());
    }

    @Test
    void testMejorarCapacidadBaul() {
        personaje.actualizarMonedas(200);
        assertTrue(personaje.mejorarCapacidadBaul(100));
        assertEquals(25, personaje.getBaul().getCapacidadTesoros());
    }

    @Test
    void testMejorarCapacidadBaulSinMonedas() {
        personaje.actualizarMonedas(50);
        assertFalse(personaje.mejorarCapacidadBaul(100));
    }
}
