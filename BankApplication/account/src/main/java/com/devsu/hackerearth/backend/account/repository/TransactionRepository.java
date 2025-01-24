package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.interfaces.IReportTransaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByAccountId(Long accountId);

    @Query("select t.date as date, a.clientId as client, a.number as accountNumber, " +
            "a.type as accountType, a.initialAmount as initialAmount, a.isActive as isActive, " +
            "t.type as transactionType, t.amount as amount, t.balance as balance " + 
            "from Transaction t inner join Account a on t.accountId = a.id where t.date between :startDate and :endDate")
    List<IReportTransaction> generateReportTransaction(Date startDate, Date endDate);

}
