package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Tesoro;

public interface AnticuarioService {
    Tesoro mejorarEstado(Long anticuarioId, Tesoro tesoro, float monedas);
}
