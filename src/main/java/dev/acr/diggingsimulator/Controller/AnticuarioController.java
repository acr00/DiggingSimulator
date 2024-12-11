package dev.acr.diggingsimulator.Controller;

import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Service.AnticuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/anticuarios")
public class AnticuarioController {

    @Autowired
    private AnticuarioService anticuarioService;

    @PostMapping("/{anticuarioId}/mejorar")
    public ResponseEntity<Tesoro> mejorarEstado(@PathVariable Long anticuarioId, @RequestBody Tesoro tesoro, @RequestParam float monedas) {
        try {
            Tesoro mejorado = anticuarioService.mejorarEstado(anticuarioId, tesoro, monedas);
            return ResponseEntity.ok(mejorado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}