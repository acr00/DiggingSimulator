package dev.acr.diggingsimulator.Model;

import dev.acr.diggingsimulator.Model.Enums.Coleccion;
import dev.acr.diggingsimulator.Model.Enums.Rareza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TesoroTest {

    private Tesoro tesoro;

    @BeforeEach
    void setUp() {
        tesoro = new Tesoro("Oro", Rareza.COMUN, 80, Coleccion.VIKINGA);
    }

    @Test
    void testCalcularValor() {
        float valorEsperado = Rareza.COMUN.getValorBase() * (80 / 100.0f) * Coleccion.VIKINGA.getMultiplicadorValor();
        assertEquals(valorEsperado, tesoro.calcularValor());
    }

    @Test
    void testCalcularValorConRarezaAlta() {
        tesoro.setRareza(Rareza.RARO);
        float valorEsperado = Rareza.RARO.getValorBase() * (80 / 100.0f) * Coleccion.VIKINGA.getMultiplicadorValor();
        assertEquals(valorEsperado, tesoro.calcularValor());
    }

    @Test
    void testCalcularValorConEstadoBajo() {
        tesoro.setEstado(20);
        float valorEsperado = Rareza.COMUN.getValorBase() * (20 / 100.0f) * Coleccion.VIKINGA.getMultiplicadorValor();
        assertEquals(valorEsperado, tesoro.calcularValor());
    }

    @Test
    void testCalcularValorConColeccionDiferente() {
        tesoro.setColeccion(Coleccion.VIKINGA);
        float valorEsperado = Rareza.COMUN.getValorBase() * (80 / 100.0f) * Coleccion.VIKINGA.getMultiplicadorValor();
        assertEquals(valorEsperado, tesoro.calcularValor());
    }

    @Test
    void testGettersYSetters() {
        tesoro.setNombre("Esmeralda");
        tesoro.setRareza(Rareza.RARO);
        tesoro.setEstado(95);
        tesoro.setColeccion(Coleccion.VIKINGA);

        assertEquals("Esmeralda", tesoro.getNombre());
        assertEquals(Rareza.RARO, tesoro.getRareza());
        assertEquals(95, tesoro.getEstado());
        assertEquals(Coleccion.VIKINGA, tesoro.getColeccion());
    }
}
