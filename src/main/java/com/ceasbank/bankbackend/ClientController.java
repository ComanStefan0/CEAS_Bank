package com.ceasbank.bankbackend;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/users")
public class ClientController {

    private final ClientRepository utilizatorRepository;
    private final ClientService utilizatorService;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired

    public ClientController(ClientRepository utilizatorRepository, ClientService utilizatorService, CredentialsRepository credentialsRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.utilizatorRepository = utilizatorRepository;
        this.utilizatorService = utilizatorService;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
//lalala
    @PostMapping("/adauga")
    public ResponseEntity<Client> adaugaUtilizator(@RequestBody Client utilizator) {
        Client savedUtilizator = utilizatorRepository.save(utilizator);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUtilizator);
    }

    @PutMapping("/depunere/{id}")
    public ResponseEntity<String> depunere(@PathVariable String id, @RequestBody Map<String, Double> body) {
        utilizatorService.depunere(id, body.get("suma"));
        return ResponseEntity.ok("Depunere reusita");
    }

    @PutMapping("/retragere/{id}")
    public ResponseEntity<String> retragere(@PathVariable String id, @RequestBody Map<String, Double> body) {
        utilizatorService.retragere(id, body.get("suma"));
        return ResponseEntity.ok("Retragere reusita");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferUtilizator(@RequestBody Map<String, String> body) {
        String id1 = body.get("id1");
        String id2 = body.get("id2");
        double suma = Double.parseDouble(body.get("suma"));
        utilizatorService.transfer(id1, id2, suma);
        return ResponseEntity.ok("Transfer reusit");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String id = body.get("id");
        String username = body.get("username");
        String password = body.get("password");

        Optional<Client> utilizatorOpt = utilizatorRepository.findById(id);
        if (!utilizatorOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizatorul cu acest ID nu a fost găsit.");
        }

        if (credentialsRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);
        Credentials credentials = new Credentials(id, username, hashedPassword);
        credentialsRepository.save(credentials);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }


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

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean exists = credentialsRepository.findByUsername(username).isPresent();
        return ResponseEntity.ok(exists);
    }
}