package com.ceasbank.bankbackend;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "Client")
public class Client {


    @Id
    private String id;
    private String nume;
    private String prenume;
    private String cnp;
    private double sold = 0;

    public Client() {
    }

    public Client(String id, String nume, String prenume, String cnp, double sold) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.sold = sold;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
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