package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
    
    List<Personaje> findByUsuario(Usuario usuario);

    
    Optional<Personaje> findByNombreAndUsuario(String nombre, Usuario usuario);

    
    int countByUsuario(Usuario usuario);

    
    List<Personaje> findByNivelGreaterThan(int nivel);

    
    List<Personaje> findByOrderByExperienciaDesc();


    Personaje findByUsername(String username);
}