package com.ceasbank.bankbackend.dto;

import lombok.*;

/**
 * DTO pentru trimiterea datelor necesare la crearea unui client nou.
 * <p>
 * Este folosit in cererile pentru inregistrarea unui client
 */

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {
    private String nume;
    private String prenume;
    private String cnp;
    private String username;
    private String password;
}
