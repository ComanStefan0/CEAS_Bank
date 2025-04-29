package com.ceasbank.bankbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class ClientController {

    @Autowired
    private ClientFunctions userDAO;

    /** Change to use @RequestBody to accept JSON data **/
    @PostMapping("/adauga")

    public String adaugaUser(@RequestBody UserRequest user) {
        try {
            userDAO.adaugaUser(user.id(), user.cnp(), user.firstName(), user.lastName(), user.sold());
            return "User adăugat cu succes!";
        } catch (Exception e) {
            return "Eroare la adăugare: " + e.getMessage();
        }
    }
    public record UserRequest(String id, String cnp, String firstName, String lastName, double sold) {}
}