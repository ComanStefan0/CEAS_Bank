package com.ceasbank.bankbackend.persistence;

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
    @OneToOne(optional = false) // 'optional = false' implies client_id can't be null
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    private double balance;
}
