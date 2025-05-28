package com.ceasbank.bankbackend.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entitate care reprezinta clientul bancar in sistem.
 * <p>
 * Fiecare client are informatii personale, credentiale de logare
 * si este asociat cu un cont bancar
 */

@Entity
@Table(name = "Client")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    /**
     * ID-ul unic al clientului. Este generat automat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nume;
    private String prenume;
    private String cnp;

    //credentials
    private String username;
    private String password;

    /**
     * Contul bancar asociat clientului.
     * <p>
     * Relatie unul la unul mapat invers din clasa {@link Account}
     * Se gestioneaza automat prin cascade si se elimina daca clientul este sters.
     */
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(hidden = true)
    private Account account;
}