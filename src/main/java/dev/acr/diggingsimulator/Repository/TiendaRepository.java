package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {
}