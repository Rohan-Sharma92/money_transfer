package com.revolut.moneytransfer.model;

import java.math.BigDecimal;

public interface IBalance extends Identifiable<String>{

	String getAccountId();

	void setAccountId(String accountId);

	Currency getCurrency();

	void setCurrency(Currency currency);

	BigDecimal getAmount();

	void setAmount(BigDecimal amount);

}
