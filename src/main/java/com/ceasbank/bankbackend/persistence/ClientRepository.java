package com.ceasbank.bankbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfata pentru accesul la datele clientilor din database.
 * <p>
 * Extinde {@link JpaRepository} pentru a oferi functionalitati CRUD
 * si include o metoda personalizata pentru a cauta un client dupa username
 */

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * Cauta un client in baza de date dupa utilizator
     *
     * @param username numele de utilizator al clientului
     * @return un {@link Optional} care contine clientul daca exista
     */
    Optional<Client> findByUsername(String username);
}

