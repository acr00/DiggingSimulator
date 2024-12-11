package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Baul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaulRepository extends JpaRepository<Baul, Long> {
    // Puedes agregar métodos personalizados si es necesario más adelante
}