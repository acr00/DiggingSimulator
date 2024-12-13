package dev.acr.diggingsimulator.Config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


public class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private jakarta.servlet.FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils, userDetailsService);
    }

    @Test
    public void testDoFilterInternal_ValidToken() throws Exception {
        String token = "valid-jwt-token";
        String username = "testuser";

        
        when(jwtUtils.getTokenFromRequest(request)).thenReturn(token);
        when(jwtUtils.validateToken(token)).thenReturn(true);
        when(jwtUtils.extractUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain, times(1)).doFilter(request, response); 
    }

    @Test
    public void testDoFilterInternal_InvalidToken() throws Exception {
        String token = "invalid-jwt-token";

        
        when(jwtUtils.getTokenFromRequest(request)).thenReturn(token);
        when(jwtUtils.validateToken(token)).thenReturn(false);

        
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response); 
    }

    @Test
    public void testDoFilterInternal_NoToken() throws Exception {
        
        when(jwtUtils.getTokenFromRequest(request)).thenReturn(null);

        
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response); 
    }

    @Test
    public void testDoFilterInternal_UserDetailsNull() throws Exception {
        String token = "valid-jwt-token";
        String username = "testuser";

        
        when(jwtUtils.getTokenFromRequest(request)).thenReturn(token);
        when(jwtUtils.validateToken(token)).thenReturn(true);
        when(jwtUtils.extractUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(null); 

        
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response); 
    }
}

