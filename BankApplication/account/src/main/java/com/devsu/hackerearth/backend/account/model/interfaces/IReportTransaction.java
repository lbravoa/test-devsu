package com.devsu.hackerearth.backend.account.model.interfaces;

import java.util.Date;

public interface IReportTransaction {

    Date getDate();
	String getClient();
	String getAccountNumber();
	String getAccountType();
	double getInitialAmount();
    boolean getIsActive();
	String getTransactionType();
	double getAmount();
	double getBalance();

}
