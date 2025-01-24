package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.controller.AccountController;
import com.devsu.hackerearth.backend.account.controller.TransactionController;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.AccountService;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@SpringBootTest
public class mainTest {

	private AccountService accountService = mock(AccountService.class);
	private AccountController accountController = new AccountController(accountService);

	private TransactionService transactionService = mock(TransactionService.class);
	private TransactionController transactionController = new TransactionController(transactionService);

	@Test
	void getAllAccountsTest() {
		// Arrange
		List<AccountDto> accounts = Arrays.asList(
			new AccountDto(1L, "number", "savings", 0.0, true, 1L),
			new AccountDto(2L, "number", "checking", 0.0, true, 1L));
		when(accountService.getAll()).thenReturn(accounts);

		// Act
		ResponseEntity<List<AccountDto>> response = accountController.getAll();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(accounts, response.getBody());
	}

	@Test
	void getAccountTest() {
		// Arrange
		AccountDto accountDto = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		when(accountService.getById(accountDto.getId())).thenReturn(accountDto);

		// Act
		ResponseEntity<AccountDto> response = accountController.get(accountDto.getId());

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(accountDto, response.getBody());
	}

	@Test
	void getAccountNotFoundTest() {
		// Arrange
		AccountDto accountDto = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		when(accountService.getById(accountDto.getId())).thenReturn(accountDto);

		// Act
		ResponseEntity<AccountDto> response = accountController.get(-1L);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void createAccountTest() {
		// Arrange
		AccountDto newAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		AccountDto createdAccount = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		when(accountService.create(newAccount)).thenReturn(createdAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.create(newAccount);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdAccount, response.getBody());
	}

	@Test
	void updateAccountTest() {
		// Arrange
		AccountDto account = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		AccountDto updatedAccount = new AccountDto(1L, "number", "savings", 0.0, false, 1L);
		when(accountService.getById(account.getId())).thenReturn(account);
		when(accountService.update(account)).thenReturn(updatedAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.update(updatedAccount.getId(), updatedAccount);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedAccount, response.getBody());
	}

	@Test
	void updateAccountNotFoundTest() {
		// Arrange
		AccountDto account = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		AccountDto updatedAccount = new AccountDto(1L, "number", "savings", 0.0, false, 1L);
		when(accountService.getById(account.getId())).thenReturn(account);
		when(accountService.update(account)).thenReturn(updatedAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.update(-1L, updatedAccount);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void partialUpdateAccountTest() {
		// Arrange
		AccountDto account = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		PartialAccountDto partialAccount = new PartialAccountDto(false);
		AccountDto updatedAccount = new AccountDto(1L, "number", "savings", 0.0, false, 1L);
		when(accountService.getById(account.getId())).thenReturn(account);
		when(accountService.partialUpdate(account.getId(), partialAccount)).thenReturn(updatedAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.partialUpdate(updatedAccount.getId(), partialAccount);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedAccount, response.getBody());
	}

	@Test
	void partialUpdateAccountNotFoundTest() {
		// Arrange
		AccountDto account = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
		PartialAccountDto partialAccount = new PartialAccountDto(false);
		AccountDto updatedAccount = new AccountDto(1L, "number", "savings", 0.0, false, 1L);
		when(accountService.getById(account.getId())).thenReturn(account);
		when(accountService.partialUpdate(account.getId(), partialAccount)).thenReturn(updatedAccount);

		// Act
		ResponseEntity<AccountDto> response = accountController.partialUpdate(-1L, partialAccount);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    void deleteAccountTest() {
        // Arrange
        AccountDto account = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
        when(accountService.getById(account.getId())).thenReturn(account);

        // Act
        ResponseEntity<Void> response = accountController.delete(account.getId());

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteAccountNotFoundTest() {
        // Arrange
        AccountDto account = new AccountDto(1L, "number", "savings", 0.0, true, 1L);
        when(accountService.getById(account.getId())).thenReturn(account);

        // Act
        ResponseEntity<Void> response = accountController.delete(-1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

	@Test
	void getAllTransactionsTest() {
		// Arrange
		List<TransactionDto> transactions = Arrays.asList(
			new TransactionDto(1L, new Date(), "deposit", 10.0, 10.0, 1L),
			new TransactionDto(2L, new Date(), "deposit", 15.0, 20.0, 2L));
		when(transactionService.getAll()).thenReturn(transactions);

		// Act
		ResponseEntity<List<TransactionDto>> response = transactionController.getAll();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(transactions, response.getBody());
	}

	@Test
	void getTransactionTest() {
		// Arrange
		TransactionDto transactionDto = new TransactionDto(1L, new Date(), "deposit", 10.0, 10.0, 1L);
		when(transactionService.getById(transactionDto.getId())).thenReturn(transactionDto);

		// Act
		ResponseEntity<TransactionDto> response = transactionController.get(transactionDto.getId());

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(transactionDto, response.getBody());
	}

	@Test
	void getTransactionNotFoundTest() {
		// Arrange
		TransactionDto transactionDto = new TransactionDto(1L, new Date(), "deposit", 10.0, 10.0, 1L);
		when(transactionService.getById(transactionDto.getId())).thenReturn(transactionDto);

		// Act
		ResponseEntity<TransactionDto> response = transactionController.get(-1L);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void createTransactionTest() {
		// Arrange
		TransactionDto newTransaction = new TransactionDto(1L, new Date(), "deposit", 10.0, 0.0, 1L);
		TransactionDto createdTransaction = new TransactionDto(1L, new Date(), "deposit", 10.0, 10.0, 1L);
		when(transactionService.create(newTransaction)).thenReturn(createdTransaction);

		// Act
		ResponseEntity<TransactionDto> response = transactionController.create(newTransaction);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(createdTransaction, response.getBody());
	}

	@Test
	void getReportTest() {
		// Arrange
		List<BankStatementDto> report = Arrays.asList(
			new BankStatementDto(new Date(), "client", "accountNumber", "accountType", 10.0, true, "transactionType", 10.0, 15.0),
			new BankStatementDto(new Date(), "client", "accountNumber", "accountType", 10.0, true, "transactionType", 5.0, 20.0));
		when(transactionService.getAllByAccountClientIdAndDateBetween(1L, new Date(), new Date())).thenReturn(report);

		// Act
		ResponseEntity<List<BankStatementDto>> response = transactionController.report(1L,  new Date(), new Date());

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(report, response.getBody());
	}
}

