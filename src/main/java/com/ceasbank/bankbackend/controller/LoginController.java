package com.ceasbank.bankbackend.controller;

import com.ceasbank.bankbackend.persistence.Client;
import com.ceasbank.bankbackend.persistence.Credentials;
import com.ceasbank.bankbackend.service.ClientService;
import com.ceasbank.bankbackend.service.CredentialsService;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping()
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ClientService clientService;
    private final CredentialsService credentialsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Autentificare reușită!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autentificare eșuată: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (credentialsService.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);
        Credentials credentials = Credentials.builder()
                .username(username)
                .password(hashedPassword)
                .build();

        credentialsService.save(credentials);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean exists = credentialsService.findByUsername(username).isPresent();
        return ResponseEntity.ok(exists);
    }
}
