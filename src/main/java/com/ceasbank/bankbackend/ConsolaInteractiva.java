package com.ceasbank.bankbackend;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConsolaInteractiva {

    private static final String BASE_URL = "http://localhost:8080/users";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n1. Inregistrare\n2. Autentificare\n3. Iesire");
            String opt = scanner.nextLine();

            switch (opt) {
                case "1" -> inregistrareCompleta();
                case "2" -> autentificare();
                case "3" -> System.exit(0);
                default -> System.out.println("Opțiune invalidă");
            }
        }
    }

    private static void inregistrareCompleta() throws Exception {
        System.out.print("ID (6 cifre): ");
        String id = scanner.nextLine();
        System.out.print("CNP (16 cifre): ");
        String cnp = scanner.nextLine();
        System.out.print("Nume: ");
        String nume = scanner.nextLine();
        System.out.print("Prenume: ");
        String prenume = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Parolă: ");
        String password = scanner.nextLine();

        // Verifică dacă username-ul există deja
        if (verificaUsernameExistente(username)) {
            System.out.println("Username-ul există deja! Alege un alt username.");
            return;
        }


        String userJson = String.format(""" 
        {
            "id":"%s",
            "cnp":"%s",
            "nume":"%s",
            "prenume":"%s",
            "sold":0.0
        }""", id, cnp, nume, prenume);

        HttpRequest reqUtilizator = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/adauga"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .build();

        HttpResponse<String> respUtilizator = client.send(reqUtilizator, HttpResponse.BodyHandlers.ofString());
        System.out.println("Utilizator: " + respUtilizator.body());

        String credentialsJson = String.format("""
        {
            "id":"%s",
            "username":"%s",
            "password":"%s"
        }""", id, username, password);

        HttpRequest reqCreds = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(credentialsJson))
                .build();

        HttpResponse<String> respCreds = client.send(reqCreds, HttpResponse.BodyHandlers.ofString());
        System.out.println("Credențiale: " + respCreds.body());
    }


    private static boolean verificaUsernameExistente(String username) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/exists?username=" + username))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().equals("true");
    }

    private static void autentificare() throws Exception {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Parolă: ");
        String password = scanner.nextLine();

        String json = String.format("""
            {
                "username":"%s",
                "password":"%s"
            }""", username, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login")) //
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Răspuns autentificare: " + response.body());
    }

}
