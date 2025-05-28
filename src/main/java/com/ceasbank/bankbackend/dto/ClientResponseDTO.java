package com.ceasbank.bankbackend.dto;

import lombok.*;

/**
 * DTO(Data Transfer Object) folosit pentru a returna informatiile unui client.
 * <p>
 * Acest obiect este utlizat in raspunsurile API-ului, fara a include date sensibile
 * precum username-ul sau parola
 */

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
