package com.ceasbank.bankbackend;

import com.ceasbank.bankbackend.ClientRepository;
import com.ceasbank.bankbackend.ClientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankbackendApplicationTests {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientService clientService;

	@BeforeEach
	void setup() {
		clientRepository.deleteAll();

		Client client1 = new Client("c1", "Ana", "Pop", "111", 100.0);
		clientRepository.save(client1);

		Client client2 = new Client("c2", "Dan", "Ionescu", "222", 200.0);
		clientRepository.save(client2);
	}

	@Test
	void testDepunereBani() {
		clientService.depunere("c1", 50.0);
		Client updated = clientRepository.findById("c1").orElseThrow();
		Assertions.assertEquals(150.0, updated.getSold());
	}

	@Test
	void testRetragereBani() {
		clientService.retragere("c1", 30.0);
		Client updated = clientRepository.findById("c1").orElseThrow();
		Assertions.assertEquals(70.0, updated.getSold());
	}

	@Test
	void testTransferBani() {
		clientService.transfer("c2", "c1", 50.0);
		Client from = clientRepository.findById("c2").orElseThrow();
		Client to = clientRepository.findById("c1").orElseThrow();

		Assertions.assertEquals(150.0, from.getSold());
		Assertions.assertEquals(150.0, to.getSold());
	}

	@Test
	void testRetragereFonduriInsuficiente() {
		RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
			clientService.retragere("c1", 500.0);
		});

		Assertions.assertEquals("Fonduri insuficiente", exception.getMessage());
	}

	@Test
	void testTransferFonduriInsuficiente() {
		RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
			clientService.transfer("c1", "c2", 500.0);
		});

		Assertions.assertEquals("Fonduri insuficiente", exception.getMessage());
	}

	@Test
	void testTransferClientInexistent() {
		RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
			clientService.transfer("c1", "c999", 10.0);
		});

		Assertions.assertEquals("Client not found", exception.getMessage());
	}
}
