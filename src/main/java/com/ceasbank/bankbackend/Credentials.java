package com.ceasbank.bankbackend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Credentials {

    @Id
    private String id;  // ID-ul utilizatorului
    private String username;  // Numele de utilizator
    private String password;  // Parola criptată

    // Constructori, getter/setter
    public Credentials() {}

    public Credentials(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
