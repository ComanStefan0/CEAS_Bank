package com.ceasbank.bankbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc  // Aceasta va configura MockMvc pentru testele tale
public class ConsolaInteractivaTest {

    @Autowired
    private MockMvc mockMvc;  // Injectează MockMvc în test

    @BeforeEach
    public void setup() {
        // Setup inițial înainte de fiecare test, dacă e necesar
    }

//    @Test
//    public void testInregistrareCompleta() throws Exception {
//        // Test pentru înregistrare completă
//        String userJson = """
//                {
//                    "id":"123456",
//                    "cnp":"1234567890123456",
//                    "nume":"Ion",
//                    "prenume":"Popescu",
//                    "sold":0.0
//                }""";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/adauga")
//                        .contentType("application/json")
//                        .content(userJson))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Client adăugat cu succes!"));
//
//        String credentialsJson = """
//                {
//                    "id":"123456",
//                    "username":"ionpopescu",
//                    "password":"parola123"
//                }""";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
//                        .contentType("application/json")
//                        .content(credentialsJson))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("User registered successfully!"));
//    }

    @Test
    public void testAutentificare() throws Exception {
        String credentialsJson = """
                {
                    "username":"ionpopescu",
                    "password":"parola123"
                }""";

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .contentType("application/json")
                        .content(credentialsJson))
                .andExpect(status().isOk())  //
                .andExpect(MockMvcResultMatchers.content().string("Autentificare reușită!"));
    }

//    @Test
//    public void testAutentificareFailure() throws Exception {
//        String credentialsJson = """
//                {
//                    "username":"ionpopescu",
//                    "password":"parolaGresita"
//                }""";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
//                        .contentType("application/json")
//                        .content(credentialsJson))
//                .andExpect(status().isUnauthorized())  // Asigură-te că este 401
//                .andExpect(MockMvcResultMatchers.content().string("Invalid password"));
//    }
}