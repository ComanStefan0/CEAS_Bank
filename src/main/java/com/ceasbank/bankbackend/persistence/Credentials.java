package com.ceasbank.bankbackend.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    @Id
    private Long id;  // ID-ul utilizatorului
    private String username;  // Numele de utilizator
    private String password;  // Parola criptată
}
