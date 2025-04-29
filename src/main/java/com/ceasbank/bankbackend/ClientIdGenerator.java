//package com.example.ceasBank;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.security.SecureRandom;
//
//@Service
//public class ClientIdGenerator {
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//    private static final int ID_LENGTH = 5;
//    private final SecureRandom random = new SecureRandom();
//
//    public String generateUniqueId() {
//        String id;
//        do {
//            id = generateRandomId();
//        } while (clientRepository.existsById(id));
//        return id;
//    }
//
//    private String generateRandomId() {
//        StringBuilder sb = new StringBuilder(ID_LENGTH);
//        for (int i = 0; i < ID_LENGTH; i++) {
//            int index = random.nextInt(CHARACTERS.length());
//            sb.append(CHARACTERS.charAt(index));
//        }
//        return sb.toString();
//    }
//}