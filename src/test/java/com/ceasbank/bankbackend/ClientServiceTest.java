package com.ceasbank.bankbackend;

import com.ceasbank.bankbackend.dto.ClientRequestDTO;
import com.ceasbank.bankbackend.dto.ClientResponseDTO;
import com.ceasbank.bankbackend.exception.ClientAlreadyExistsException;
import com.ceasbank.bankbackend.exception.ClientNotFoundException;
import com.ceasbank.bankbackend.persistence.Account;
import com.ceasbank.bankbackend.persistence.AccountRepository;
import com.ceasbank.bankbackend.persistence.Client;
import com.ceasbank.bankbackend.persistence.ClientRepository;
import com.ceasbank.bankbackend.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Teste unitare pentru clasa {@link ClientService}.
 * <p>
 * verifica comportamentul serviciului de gestionare a clientilor inclusiv
 * - inregistrarea unui client nou si crearea contului asociat
 * - tratarea cazului în care username-ul exista deja
 * - obtinerea datelor unui client dupa ID
 * - stergerea unui client
 * - si gestionarea exceptiilor corespunzatoare
 */
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientService clientService;

    /**
     * Initializeaza mock-urile inainte de rularea fiecarui test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testeaza salvarea unui client nou impreuna cu crearea contului bancar
     */
    @Test
    void saveClient_shouldCreateClientAndAccount() {
        // Given
        ClientRequestDTO request = new ClientRequestDTO("John", "Doe", "1234567890123", "johndoe", "secret");

        when(clientRepository.findByUsername("johndoe")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secret")).thenReturn("encodedSecret");

        Client savedClient = Client.builder()
                .id(1L)
                .nume("John")
                .prenume("Doe")
                .cnp("1234567890123")
                .username("johndoe")
                .password("encodedSecret")
                .build();

        when(clientRepository.save(any())).thenReturn(savedClient);
        when(accountRepository.save(any())).thenAnswer(i -> i.getArgument(0)); // return the account passed in

        // When
        ClientResponseDTO response = clientService.saveClient(request);

        // Then
        assertEquals("John", response.getNume());
        assertEquals("Doe", response.getPrenume());
        assertEquals("1234567890123", response.getCnp());

        verify(clientRepository).save(any(Client.class));
        verify(accountRepository).save(any(Account.class));
    }

    /**
     * Testeaza cazul in care se incearca salvarea unui client cu username deja existent.
     */
    @Test
    void saveClient_shouldThrowExceptionIfUsernameExists() {
        // Given
        ClientRequestDTO request = new ClientRequestDTO("Jane", "Smith", "9999999999999", "janesmith", "pass");
        when(clientRepository.findByUsername("janesmith")).thenReturn(Optional.of(new Client()));

        // Then
        assertThrows(ClientAlreadyExistsException.class, () -> clientService.saveClient(request));
    }

    /**
     * Testeaza returnarea datelor unui client pe baza ID-ului
     */
    @Test
    void getClientById_shouldReturnClientDetails() {
        // Given
        Client client = Client.builder()
                .id(1L)
                .nume("John")
                .prenume("Doe")
                .cnp("1234567890123")
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // When
        ClientResponseDTO result = clientService.getClientById(1L);

        // Then
        assertEquals("John", result.getNume());
        assertEquals("Doe", result.getPrenume());
        assertEquals("1234567890123", result.getCnp());
    }

    /**
     * Testeaza cazul in care clientul cautat nu este gasit
     */
    @Test
    void getClientById_shouldThrowIfNotFound() {
        // Given
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        // Then
        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(99L));
    }

    /**
     * Testeaza stergerea unui client pe baza ID-ului.
     */
    @Test
    void deleteClient_shouldDeleteClientById() {
        // When
        clientService.deleteClient(1L);

        // Then
        verify(clientRepository).deleteById(1L);
    }
}
