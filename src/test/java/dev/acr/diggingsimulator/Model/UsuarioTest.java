package dev.acr.diggingsimulator.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class UsuarioTest {

    @Test
    public void testUsuarioCreation() {
        Usuario usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("password123");
        usuario.setEmail("test@example.com");
        usuario.setRole(Usuario.Role.USER);

        Assertions.assertEquals("testuser", usuario.getUsername());
        Assertions.assertEquals("password123", usuario.getPassword());
        Assertions.assertEquals("test@example.com", usuario.getEmail());
        Assertions.assertEquals(Usuario.Role.USER, usuario.getRole());
    }

    @Test
    public void testUsuarioRoleAdmin() {
        Usuario usuario = new Usuario();
        usuario.setRole(Usuario.Role.ADMIN);

        Assertions.assertEquals(Usuario.Role.ADMIN, usuario.getRole());
    }

    @Test
    public void testUsuarioEmailValidation() {
        Usuario usuario = new Usuario();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            usuario.setEmail("invalidemail");
        });

        String expectedMessage = "Formato de email inválido";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
