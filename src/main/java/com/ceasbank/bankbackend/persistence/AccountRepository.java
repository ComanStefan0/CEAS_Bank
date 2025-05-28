package com.ceasbank.bankbackend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfata pentru accesul la datele conturilor bancare.
 * <p>
 * Extinde {@link JpaRepository} pentru a oferi operatii CRUD implicite si metode personalizate
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Gaseste un cont bancar pe baza ID-ului clientului asociat.
     *
     * @param id ID-ul clientului
     * @return un {@link Optional} care contine contul daca exista
     */
    Optional<Account> findByClientId(Long id);
}
