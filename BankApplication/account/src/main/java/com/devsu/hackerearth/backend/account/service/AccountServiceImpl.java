package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.handler.exceptions.RegisterNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ObjectMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;

        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(o -> mapper.convertValue(o, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        return mapper.convertValue(accountRepository.findById(id).orElseThrow(() -> new RegisterNotFoundException()),
                AccountDto.class);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        Account account = accountRepository.save(mapper.convertValue(accountDto, Account.class));
        return mapper.convertValue(account, AccountDto.class);
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        Account account = accountRepository.save(mapper.convertValue(accountDto, Account.class));
        return mapper.convertValue(account, AccountDto.class);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        AccountDto accountDto = mapper.convertValue(
                accountRepository.findById(id).orElseThrow(() -> new RegisterNotFoundException()), AccountDto.class);
        accountDto.setActive(partialAccountDto.isActive());

        Account account = accountRepository.save(mapper.convertValue(accountDto, Account.class));

        return mapper.convertValue(account, AccountDto.class);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

}
