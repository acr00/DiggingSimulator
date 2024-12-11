package dev.acr.diggingsimulator.Service;

import dev.acr.diggingsimulator.Model.Enums.UserRole;
import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    public class UserAlreadyExistsException extends RuntimeException {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        validarUsuarioExistente(usuario);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    private void validarUsuarioExistente(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new UserAlreadyExistsException("El nombre de usuario ya existe");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new UserAlreadyExistsException("El email ya está registrado");
        }
    }

    @Transactional
public Usuario cambiarRol(Long usuarioId, UserRole nuevoRol, Usuario admin) {
    verificarAdmin(admin);

    Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
    if (usuarioOptional.isEmpty()) {
        throw new EntityNotFoundException("Usuario no encontrado con id: " + usuarioId);
    }

    Usuario usuario = usuarioOptional.get();
    usuario.setRole(nuevoRol);
    return usuarioRepository.save(usuario);
}

    public List<Usuario> obtenerTodosLosUsuarios(Usuario admin) {
        verificarAdmin(admin);
        return usuarioRepository.findAll();
    }

    private void verificarAdmin(Usuario admin) {
        if (!admin.esAdmin()) {
            throw new SecurityException("Solo un administrador puede realizar esta acción");
        }
    }
}
