package com.ceasbank.bankbackend.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {
    private String nume;
    private String prenume;
    private String cnp;
}
