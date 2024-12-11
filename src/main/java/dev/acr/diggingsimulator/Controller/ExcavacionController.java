package dev.acr.diggingsimulator.Controller;

import dev.acr.diggingsimulator.Service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/excavacion")
public class ExcavacionController {

    @Autowired
    private PersonajeService personajeService;

    @PostMapping("/{personajeId}")
    public ResponseEntity<List<Object>> excavar(@PathVariable Long personajeId, @RequestParam int energiaGastada) {
        try {
            List<Object> objetosEncontrados = personajeService.excavar(personajeId, energiaGastada);
            return ResponseEntity.ok(objetosEncontrados);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}


