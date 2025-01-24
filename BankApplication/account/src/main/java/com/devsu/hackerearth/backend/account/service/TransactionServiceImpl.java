package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.handler.exceptions.BalanceException;
import com.devsu.hackerearth.backend.account.handler.exceptions.RegisterNotFoundException;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.interfaces.IReportTransaction;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ObjectMapper mapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;

        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(o -> mapper.convertValue(o, TransactionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        return mapper.convertValue(
                transactionRepository.findById(id).orElseThrow(() -> new RegisterNotFoundException()),
                TransactionDto.class);
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        TransactionDto lastTransactionDto = getLastByAccountId(transactionDto.getAccountId());
        double balance = lastTransactionDto.getBalance() + transactionDto.getAmount();
        if (balance < 0.0) {
            throw new BalanceException("Saldo no disponible");
        }

        transactionDto.setId(null);
        transactionDto.setBalance(balance);
        transactionDto.setDate(new Date());

        Transaction transaction = transactionRepository.save(mapper.convertValue(transactionDto, Transaction.class));
        return mapper.convertValue(transaction, TransactionDto.class);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        return transactionRepository.generateReportTransaction(dateTransactionStart, dateTransactionEnd)
                .stream()
                .map(o -> mapper.convertValue(o, BankStatementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        return mapper.convertValue(transactionRepository.findByAccountId(accountId), TransactionDto.class);
    }

}
