package com.ceasbank.bankbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ClientController {

    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientController(ClientRepository clientRepository, ClientService clientService,
                            CredentialsRepository credentialsRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/adauga")
    public String adaugaClient(@RequestBody Client client) {
        clientRepository.save(client);
        return "Client adăugat cu succes!";
    }

    @PutMapping("/depunere/{id}")
    public String depunere(@PathVariable String id, @RequestBody Map<String, Double> body) {
        clientService.depunere(id, body.get("suma"));
        return "Depunere reusita";
    }

    @PutMapping("/retragere/{id}")
    public String retragere(@PathVariable String id, @RequestBody Map<String, Double> body) {
        clientService.retragere(id, body.get("suma"));
        return "Retragere reusita";
    }

    @PostMapping("/transfer")
    public String transferClient(@RequestBody Map<String, String> body) {
        String id1 = body.get("id1");
        String id2 = body.get("id2");
        double suma = Double.parseDouble(body.get("suma"));
        clientService.transfer(id1, id2, suma);
        return "Transfer reusit";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String id = body.get("id");
        String username = body.get("username");
        String password = body.get("password");

        Optional<Client> clientOpt = clientRepository.findById(id);
        if (!clientOpt.isPresent()) {
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
    public String login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Credentials creds = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, creds.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return "Autentificare reușită!";
    }
}