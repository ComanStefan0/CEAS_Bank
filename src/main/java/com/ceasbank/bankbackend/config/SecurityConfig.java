package com.ceasbank.bankbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuratia de securitate pentru aplicatia CEAS Bank
 * <p>
 * Aceasta clasa contine:
 * -Securitatea HTTP
 * -CORS
 * -CSRF
 * -Encoder-ul de parole
 * -Managerul de autentificare
 */

@Configuration
public class SecurityConfig {

    /**
     * Realizeaza un encoder de parole care foloseste algoritmul BCrypt
     *
     * @return un obiect {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Se creeaza regulile de securitate pentru cererile HTTP.
     * - Permite accesul public la anumite rute (ex: Swagger, signup, login)
     * - Necesita autentificare pentru restul rutelor
     * - Dezactiveaza CSRF si FrameOptions
     *
     * @param http obiectul HttpSecurity folosit pentru configurare
     * @return lantul de filtre de securitate
     * @throws Exception daca apare o eroare la configurare
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/h2-console/**",
                                "/users",
                                "/users/",
                                "/users/**",
                                "/h2-console/**",
                                "/users/signup",
                                "/login",
                                "/account/**",
                                "/error"
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

    /**
     * Configureaza setarile CORS pentru a permite cereri din orice origine.
     *
     * @return sursa de configuratie CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Expune managerul de autentificare ca un bean Spring.
     *
     * @param config obiectul {@link AuthenticationConfiguration}
     * @return managerul de autentificare
     * @throws Exception daca apare o eroare la obtinerea managerului
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}


