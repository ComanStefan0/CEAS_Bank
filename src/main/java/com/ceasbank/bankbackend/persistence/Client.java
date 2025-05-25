package com.ceasbank.bankbackend.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Client")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nume;
    private String prenume;
    private String cnp;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(hidden = true)
    private Account account;

}