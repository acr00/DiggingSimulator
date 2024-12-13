package dev.acr.diggingsimulator.Model.Enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RarezaTest {

    @Test
    void testMuyComún() {
        Rareza rareza = Rareza.MUY_COMUN;

        assertEquals(0.5f, rareza.getProbabilidad());
        assertEquals(10, rareza.getValorBase());
    }

    @Test
    void testComún() {
        Rareza rareza = Rareza.COMUN;

        assertEquals(0.3f, rareza.getProbabilidad());
        assertEquals(50, rareza.getValorBase());
    }

    @Test
    void testRaro() {
        Rareza rareza = Rareza.RARO;

        assertEquals(0.15f, rareza.getProbabilidad());
        assertEquals(200, rareza.getValorBase());
    }

    @Test
    void testMuyRaro() {
        Rareza rareza = Rareza.MUY_RARO;

        assertEquals(0.04f, rareza.getProbabilidad());
        assertEquals(500, rareza.getValorBase());
    }

    @Test
    void testExtremadamenteRaro() {
        Rareza rareza = Rareza.EXTREMADAMENTE_RARO;

        assertEquals(0.01f, rareza.getProbabilidad());
        assertEquals(1000, rareza.getValorBase());
    }

    @Test
    void testGetProbabilidad() {
        assertEquals(0.5f, Rareza.MUY_COMUN.getProbabilidad());
        assertEquals(0.3f, Rareza.COMUN.getProbabilidad());
        assertEquals(0.15f, Rareza.RARO.getProbabilidad());
        assertEquals(0.04f, Rareza.MUY_RARO.getProbabilidad());
        assertEquals(0.01f, Rareza.EXTREMADAMENTE_RARO.getProbabilidad());
    }

    @Test
    void testGetValorBase() {
        assertEquals(10, Rareza.MUY_COMUN.getValorBase());
        assertEquals(50, Rareza.COMUN.getValorBase());
        assertEquals(200, Rareza.RARO.getValorBase());
        assertEquals(500, Rareza.MUY_RARO.getValorBase());
        assertEquals(1000, Rareza.EXTREMADAMENTE_RARO.getValorBase());
    }

    @Test
    void testEnumValues() {
        Rareza[] rarezas = Rareza.values();
        assertEquals(5, rarezas.length);
        assertTrue(rarezas[0] == Rareza.MUY_COMUN);
        assertTrue(rarezas[1] == Rareza.COMUN);
        assertTrue(rarezas[2] == Rareza.RARO);
        assertTrue(rarezas[3] == Rareza.MUY_RARO);
        assertTrue(rarezas[4] == Rareza.EXTREMADAMENTE_RARO);
    }
}
