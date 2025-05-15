package com.ceasbank.bankbackend;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Service
public class DatabaseBackupService {

    private static final String DATABASE_URL = "jdbc:h2:file:./data/ceasbankdb";  // URL-ul bazei de date
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    @Scheduled(cron = "0 0 2 * * ?")  // Programarea backup-ului zilnic la 2 AM
    public void backupDatabase() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            String backupFilePath = "./data/backup_" + System.currentTimeMillis() + ".sql";
            String sqlScript = "SCRIPT TO '" + backupFilePath + "'";  // Scriptul SQL pentru backup
            try (Statement statement = connection.createStatement()) {
                statement.execute(sqlScript);
                System.out.println("Backup a fost realizat cu succes: " + backupFilePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Eroare la realizarea backup-ului: " + e.getMessage());
        }
    }
}