package com.revolut.moneytransfer.model.impl;

import java.math.BigDecimal;

import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IBalance;

public class Balance implements IBalance {

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

	@Override
	public String getAccountId() {
		return accountId;
	}

	@Override
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public Currency getCurrency() {
		return currency;
	}

	@Override
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
