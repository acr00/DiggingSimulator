package dev.acr.diggingsimulator.Model.Enums;

public enum Rareza {
    MUY_COMUN(0.5f, 10),      // 50% de probabilidad, valor base 10
    COMUN(0.3f, 50),           // 30% de probabilidad, valor base 50
    RARO(0.15f, 200),          // 15% de probabilidad, valor base 200
    MUY_RARO(0.04f, 500),      // 4% de probabilidad, valor base 500
    EXTREMADAMENTE_RARO(0.01f, 1000); // 1% de probabilidad, valor base 1000

    private final float probabilidad;
    private final int valorBase;

    Rareza(float probabilidad, int valorBase) {
        this.probabilidad = probabilidad;
        this.valorBase = valorBase;
    }

    public float getProbabilidad() {
        return probabilidad;
    }

    public int getValorBase() {
        return valorBase;
    }

}
