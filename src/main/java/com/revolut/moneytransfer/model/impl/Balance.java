package com.revolut.moneytransfer.model.impl;

import java.math.BigDecimal;

import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.Identifiable;

public class Balance implements Identifiable<String> {

	private BigDecimal amount = BigDecimal.ZERO;

	private String accountId;

	private Currency currency;

	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
