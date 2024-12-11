package dev.acr.diggingsimulator.Model.Enums;

public enum UserRole {
    USER("Usuario estándar con permisos básicos"),
    ADMIN("Usuario con permisos administrativos completos");

    private final String descripcion;

    UserRole(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}