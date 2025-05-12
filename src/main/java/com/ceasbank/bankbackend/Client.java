package com.ceasbank.bankbackend;
import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "UTILIZATOR")
public class Client {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String cnp;
    private double sold = 0;

    public Client() {
    }

    public Client(String id, String firstName, String lastName, String cnp, double sold) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.sold = sold;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}