package dev.acr.diggingsimulator.Controller;

import dev.acr.diggingsimulator.Model.Personaje;
import dev.acr.diggingsimulator.Service.PersonajeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @PostMapping
    public ResponseEntity<?> crearPersonaje(@RequestBody Personaje personaje, @RequestParam Long usuarioId) {
        try {
            Personaje nuevoPersonaje = personajeService.crearPersonaje(personaje, usuarioId);
            return ResponseEntity.ok(nuevoPersonaje);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{personajeId}/excavar")
    public ResponseEntity<?> excavar(@PathVariable Long personajeId, @RequestParam int energiaGastada) {
        try {
            List<Object> objetosEncontrados = personajeService.excavar(personajeId, energiaGastada);
            return ResponseEntity.ok(objetosEncontrados);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPersonajeDeUsuario(@PathVariable Long usuarioId) {
        try {
            List<Personaje> personajes = personajeService.obtenerPersonajesDeUsuario(usuarioId);
            if (personajes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(personajes.get(0));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}