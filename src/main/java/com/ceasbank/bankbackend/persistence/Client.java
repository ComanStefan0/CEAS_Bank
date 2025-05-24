package com.ceasbank.bankbackend.persistence;

import jakarta.persistence.*;
import lombok.*;

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
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Account account;

}