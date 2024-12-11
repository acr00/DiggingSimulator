package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Tesoro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesoroRepository extends JpaRepository<Tesoro, Long> {
}