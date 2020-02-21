package com.revolut.moneytransfer.requestwrappers;

import java.math.BigDecimal;

import com.revolut.moneytransfer.model.Currency;

public class WalletRequestWrapper {

	private String accountId;

	private BigDecimal amount;

	private Currency currency;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
