package com.ceasbank.bankbackend.dto;

import lombok.*;

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
