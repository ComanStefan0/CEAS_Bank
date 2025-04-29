package com.ceasbank.bankbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClientFunctions {

    @Autowired
    private DataSource dataSource;

    @Transactional
    public void adaugaUser(String id, String cnp, String prenume, String nume, double sold) throws SQLException {
        String sql = "INSERT INTO USERS (ID, CNP, FIRST_NAME, LAST_NAME, SOLD) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, cnp);
            ps.setString(3, prenume);
            ps.setString(4, nume);
            ps.setDouble(5, sold);
            ps.executeUpdate();
        }
    }
}