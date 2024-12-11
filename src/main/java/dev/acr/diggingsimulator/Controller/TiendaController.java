package dev.acr.diggingsimulator.Controller;

import dev.acr.diggingsimulator.Model.Baul;
import dev.acr.diggingsimulator.Model.Tesoro;
import dev.acr.diggingsimulator.Service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @PostMapping("/{tiendaId}/mejora")
    public ResponseEntity<String> comprarMejora(@PathVariable Long tiendaId, @RequestBody Baul baul) {
        try {
            boolean mejorado = tiendaService.comprarMejora(tiendaId, baul);
            return ResponseEntity.ok(mejorado ? "Mejora comprada exitosamente." : "No se pudo comprar la mejora.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{tiendaId}/vender-tesoro")
public ResponseEntity<Float> venderTesoro(@PathVariable Long tiendaId, @RequestBody Tesoro tesoro) {
    try {
        float monedas = tiendaService.venderTesoro(tiendaId, tesoro);
        return ResponseEntity.ok(monedas);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(0.0f);
    }
}
}
