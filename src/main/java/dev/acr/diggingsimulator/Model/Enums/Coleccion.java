package dev.acr.diggingsimulator.Model.Enums;

public enum Coleccion {
    EGIPCIA("Colección de artefactos egipcios antiguos", 1.2f),
    VIKINGA("Colección de reliquias vikingas", 1.5f);

    private final String descripcion;
    private final float multiplicadorValor;

    Coleccion(String descripcion, float multiplicadorValor) {
        this.descripcion = descripcion;
        this.multiplicadorValor = multiplicadorValor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getMultiplicadorValor() {
        return multiplicadorValor;
    }
}