package dev.acr.diggingsimulator.Repository;

import dev.acr.diggingsimulator.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  
    Optional<Usuario> findByUsername(String username);

   
    Optional<Usuario> findByEmail(String email);


    boolean existsByUsername(String username);

   
    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Personaje p WHERE p.usuario.id = :usuarioId")
    boolean hasPersonaje(@Param("usuarioId") Long usuarioId);
    
}
