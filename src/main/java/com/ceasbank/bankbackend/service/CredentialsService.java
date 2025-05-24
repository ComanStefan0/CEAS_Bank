package com.ceasbank.bankbackend.service;

import com.ceasbank.bankbackend.persistence.Credentials;
import com.ceasbank.bankbackend.persistence.CredentialsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CredentialsService {
    private CredentialsRepository credentialsRepository;

    public Optional<Credentials> findByUsername(String username) {
        return credentialsRepository.findByUsername(username);
    }

    public Credentials save(Credentials credentials) {
        return credentialsRepository.save(credentials);
    }
}
