package com.ceasbank.bankbackend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials, String> {
    // Metoda de căutare după username
    Optional<Credentials> findByUsername(String username);
}