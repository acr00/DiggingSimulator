package dev.acr.diggingsimulator.Model.Enums;

public enum TipoEfecto {
    AUMENTAR_PROBABILIDAD_TESOROS("Aumenta la probabilidad de encontrar tesoros raros", 0.2f),
    VELOCIDAD("Incrementa el valor al vender el siguiente objeto", 1.5f);
    

    private final String descripcion;
    private final float valorEfecto;

    TipoEfecto(String descripcion, float valorEfecto) {
        this.descripcion = descripcion;
        this.valorEfecto = valorEfecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getValorEfecto() {
        return valorEfecto;
    }
}