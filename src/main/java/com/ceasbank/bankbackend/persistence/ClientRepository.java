package com.ceasbank.bankbackend.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    void deleteClientById(Long id);
}

