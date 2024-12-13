package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Baul;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BaulRepositoryTest {

    @Autowired
    private BaulRepository baulRepository;

    private Baul baul;

    @BeforeEach
    void setUp() {

        baul = new Baul();
        baul.setCapacidadTesoros(20);
    }

    @Test
    void testGuardarYObtenerBaul() {

        Baul savedBaul = baulRepository.save(baul);

        assertNotNull(savedBaul.getBaulId());

        Baul foundBaul = baulRepository.findById(savedBaul.getBaulId()).orElse(null);

        assertNotNull(foundBaul);
        assertEquals(savedBaul.getBaulId(), foundBaul.getBaulId());
    }

    @Test
    void testActualizarBaul() {

        Baul savedBaul = baulRepository.save(baul);
        

        savedBaul.setCapacidadTesoros(30);
        
 
        Baul updatedBaul = baulRepository.save(savedBaul);
        
    
        assertEquals(30, updatedBaul.getCapacidadTesoros());
    }

    @Test
    void testEliminarBaul() {

        Baul savedBaul = baulRepository.save(baul);

        baulRepository.delete(savedBaul);
        

        assertFalse(baulRepository.existsById(savedBaul.getBaulId()));
    }


}