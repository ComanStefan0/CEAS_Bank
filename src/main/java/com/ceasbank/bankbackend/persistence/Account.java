package com.ceasbank.bankbackend.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Account")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    @JsonBackReference
    @Schema(implementation = Account.class)
    private Client client;

}
