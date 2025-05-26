package com.ceasbank.bankbackend.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entitate care reprezinta un cont bancar asociat unui client.
 *
 * Fiecare cont este legat de un singur client si contine un sold
 */

@Entity
@Table(name = "Account")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    /**
     * ID-ul unic al contului. Se genereaza automat
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;
    /**
     * Clientul asociat acestui cont.
     *
     * Fiecare cont apartine unui singur client.
     * Se foloseste {@link JsonBackReference} pentru a evita recursivitatea in serializare.
     */
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    @JsonBackReference
    @Schema(implementation = Account.class)
    private Client client;

}
