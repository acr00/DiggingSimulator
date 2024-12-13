package dev.acr.diggingsimulator.Config;

import dev.acr.diggingsimulator.Model.Usuario;
import dev.acr.diggingsimulator.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService){
        return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{

        http.csrf(x -> x.disable());
        http.sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(x -> x.disable());
        http.httpBasic(x -> x.disable());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(x -> x
            .requestMatchers("/ds/auth/**").permitAll()
            .requestMatchers("/ds/admin/**").hasRole(Usuario.Role.ROLE_ADMIN.name())
            .requestMatchers("/ds/**").hasAnyRole(Usuario.Role.ROLE_ADMIN.name(),Usuario.Role.ROLE_USER.name())
            .anyRequest().authenticated());
        return http.build();

    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
            return org.springframework.security.core.userdetails.User.builder()
            .username(usuario.getUsername())
            .password(usuario.getPassword())
            .authorities("ROLE_"+usuario.getRole().name())
            .build();
        };
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public CommandLineRunner runner(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
    return args -> {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario rootUser = Usuario.builder()
                    .role(Usuario.Role.ROLE_ADMIN)
                    .username("admin")
                    .email("email@mail.com")
                    .password(passwordEncoder.encode("password"))
                    .build();
            usuarioRepository.save(rootUser);
            }
        };
    }


}