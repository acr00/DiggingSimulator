package dev.acr.diggingsimulator.Model.Enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColeccionTest {

    @Test
    void testColeccionEgipcia() {
        Coleccion coleccion = Coleccion.EGIPCIA;

        assertEquals("Colección de artefactos egipcios antiguos", coleccion.getDescripcion());
        assertEquals(1.2f, coleccion.getMultiplicadorValor());
    }

    @Test
    void testColeccionVikinga() {
        Coleccion coleccion = Coleccion.VIKINGA;

        assertEquals("Colección de reliquias vikingas", coleccion.getDescripcion());
        assertEquals(1.5f, coleccion.getMultiplicadorValor());
    }

    @Test
    void testGetDescripcion() {
        assertEquals("Colección de artefactos egipcios antiguos", Coleccion.EGIPCIA.getDescripcion());
        assertEquals("Colección de reliquias vikingas", Coleccion.VIKINGA.getDescripcion());
    }

    @Test
    void testGetMultiplicadorValor() {
        assertEquals(1.2f, Coleccion.EGIPCIA.getMultiplicadorValor());
        assertEquals(1.5f, Coleccion.VIKINGA.getMultiplicadorValor());
    }

    @Test
    void testEnumValues() {
        Coleccion[] colecciones = Coleccion.values();
        assertEquals(2, colecciones.length);
        assertTrue(colecciones[0] == Coleccion.EGIPCIA);
        assertTrue(colecciones[1] == Coleccion.VIKINGA);
    }
}