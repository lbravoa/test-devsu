package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransactionDto>> getAll() {
		return ResponseEntity.ok(transactionService.getAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionDto> get(@PathVariable Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id is missing");
		}
		return ResponseEntity.ok(transactionService.getById(id));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto) {
		return ResponseEntity.ok(transactionService.create(transactionDto));
	}

	@GetMapping(value = "/clients/{clientId}/report", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {
		// api/transactions/clients/{clientId}/report
		// Get report
		return null;
	}
}
