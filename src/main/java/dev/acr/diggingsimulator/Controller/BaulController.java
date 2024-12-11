package dev.acr.diggingsimulator.Controller;

import dev.acr.diggingsimulator.Model.Consumible;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Service.BaulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/baules")
public class BaulController {

    @Autowired
    private BaulService baulService;

    @PostMapping("/{baulId}/consumibles")
    public ResponseEntity<String> agregarConsumible(@PathVariable Long baulId, @RequestBody Consumible consumible) {
        try {
            baulService.agregarConsumible(baulId, consumible);
            return ResponseEntity.ok("Consumible agregado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{baulId}/tesoros")
    public ResponseEntity<String> agregarTesoro(@PathVariable Long baulId, @RequestBody Tesoro tesoro) {
        try {
            baulService.agregarTesoro(baulId, tesoro);
            return ResponseEntity.ok("Tesoro agregado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

