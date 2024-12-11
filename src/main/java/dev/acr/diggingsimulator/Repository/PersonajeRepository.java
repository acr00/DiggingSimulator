package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
    // Buscar personajes de un usuario específico
    List<Personaje> findByUsuario(Usuario usuario);

    // Buscar personaje por nombre y usuario
    Optional<Personaje> findByNombreAndUsuario(String nombre, Usuario usuario);

    // Contar personajes de un usuario
    int countByUsuario(Usuario usuario);

    // Buscar personajes por nivel
    List<Personaje> findByNivelGreaterThan(int nivel);

    // Buscar personajes ordenados por experiencia
    List<Personaje> findByOrderByExperienciaDesc();
}