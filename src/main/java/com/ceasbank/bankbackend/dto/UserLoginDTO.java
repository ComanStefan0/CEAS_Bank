package com.ceasbank.bankbackend.dto;

import lombok.*;

/**
 * DTO folosit pentru autentificarea clientilor
 * Acest obiect este trimis de utilizator in timpul procesului de login.
 */

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    /**
     * Username-ul clientului
     */
    private String username;
    /**
     * Parola asociata contului clientului.
     */
    private String password;
}
