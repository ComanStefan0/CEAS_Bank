package com.ceasbank.bankbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    // Metoda de căutare după username
    Optional<Credentials> findByUsername(String username);
}