package dev.acr.diggingsimulator.Controller;

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

    @PostMapping("/agregar-tesoro")
    public ResponseEntity<String> agregarTesoro(@RequestParam Long baulId, @RequestBody Tesoro tesoro) {
        baulService.agregarTesoro(baulId, tesoro);
        return ResponseEntity.ok("Tesoro agregado al baúl.");
    }

}

