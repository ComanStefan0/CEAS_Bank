package com.ceasbank.bankbackend.controller;

import com.ceasbank.bankbackend.dto.UserLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsabil de autentificarea utilizatorilor
 *
 * Expune endpointul /login care primeste un username si o parola
 * si verifica daca acestea sunt valide
 */

@RestController
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    /**
     * Autentifica un utilizator pe baza credentialelor
     * @param userLoginDTO obiect care contine username-ul si parola introduse de utlizator
     * @return mesaj de succes daca autentificarea reuseste, sau mesaj de eroare daca nu functioneaza
     */
    @Operation(
            summary = "User login",
            description = "Authenticates a user based on the provided username and password. Returns a success message upon successful authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Authentication failed: invalid username or password"),
            @ApiResponse(responseCode = "400", description = "Bad request: malformed or missing credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("your account has been logged in!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you didn't login!" + e.getMessage());
        }
    }
}
