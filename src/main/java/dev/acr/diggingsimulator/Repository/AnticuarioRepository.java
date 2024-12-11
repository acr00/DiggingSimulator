package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Anticuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnticuarioRepository extends JpaRepository<Anticuario, Long> {
}
