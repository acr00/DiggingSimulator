package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Enums.UserRole;
import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Verificar si el usuario o email ya existen
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Guardar usuario
        return usuarioRepository.save(usuario);
    }

    // Método para cambiar rol (solo por admin)
    @Transactional
    public Usuario cambiarRol(Long usuarioId, UserRole nuevoRol, Usuario admin) {
        if (!admin.esAdmin()) {
            throw new SecurityException("Solo un administrador puede cambiar roles");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setRole(nuevoRol);
        return usuarioRepository.save(usuario);
    }

    // Método para obtener todos los usuarios (solo para admin)
    public List<Usuario> obtenerTodosLosUsuarios(Usuario admin) {
        if (!admin.esAdmin()) {
            throw new SecurityException("Solo un administrador puede ver todos los usuarios");
        }
        return usuarioRepository.findAll();
    }
}