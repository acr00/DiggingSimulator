package dev.acr.diggingsimulator.Config;


import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class JwtUtilsTest {

    private JwtUtils jwtUtils;
    
    @Value("${jwt.secret}")
    private String secret;


    @Test
    public void testGenerateToken() {
        String username = "testuser";
        String token = jwtUtils.generateToken(username);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    public void testGetTokenFromRequest() {
        // Creando una solicitud HTTP mock
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String token = "Bearer mytesttoken";
        Mockito.when(request.getHeader("Authorization")).thenReturn(token);

        String tokenFromRequest = jwtUtils.getTokenFromRequest(request);

        assertEquals("mytesttoken", tokenFromRequest);
    }

    @Test
    public void testValidateToken_ValidToken() {
        String username = "testuser";
        String token = jwtUtils.generateToken(username);

        boolean isValid = jwtUtils.validateToken(token);

        assertTrue(isValid);
    }


    @Test
    public void testValidateToken_MalformedToken() {
        String malformedToken = "malformedtoken";

        boolean isValid = jwtUtils.validateToken(malformedToken);

        assertFalse(isValid);
    }

    @Test
    public void testExtractUsername() {
        String username = "testuser";
        String token = jwtUtils.generateToken(username);

        String extractedUsername = jwtUtils.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    public void testExtractUsername_InvalidToken() {
        String invalidToken = "malformedtoken";

        assertThrows(SecurityException.class, () -> {
            jwtUtils.extractUsername(invalidToken);
        });
    }
}
