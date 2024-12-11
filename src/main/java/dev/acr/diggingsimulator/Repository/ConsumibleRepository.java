package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Consumible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumibleRepository extends JpaRepository<Consumible, Long> {
    // Puedes agregar métodos personalizados si los necesitas
}
