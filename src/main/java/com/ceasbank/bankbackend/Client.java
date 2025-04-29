package com.ceasbank.bankbackend;
import jakarta.persistence.Entity;


import jakarta.persistence.Id;

@Entity
public class Client {

    @Id
    private String Id;
    private String FirstName;
    private String LastName;
    private String CNP;
    private double sold = 0;

    public Client() {
    }

    public Client(String ID, String firstName, String lastName, String CNP, double sold) {
        this.Id = ID;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.CNP = CNP;
        this.sold = sold;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getCNP() {
        return CNP;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public void setId(String ID) {
        this.Id = ID;
    }

    public String getId() {
        return Id;
    }
}