package com.revolut.moneytransfer.services.impl;

import java.math.BigDecimal;

import com.revolut.moneytransfer.handlers.StandardResponse;
import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.services.IMoneyTransferOperationService;

public class MockMoneyTransferOperationService implements IMoneyTransferOperationService {

	private StandardResponse response;
	private boolean isAddAmountInvoked;
	private boolean isTransferAmountInvoked;

	@Override
	public StandardResponse addAmount(IAccount account, BigDecimal amount, Currency currency) {
		isAddAmountInvoked = true;
		return response;
	}

	@Override
	public StandardResponse transferAmount(IAccount creditAccount, IAccount debitAccount, BigDecimal amount,
			Currency currency) {
		isTransferAmountInvoked = true;
		return response;
	}

	public void setExpectedResponse(StandardResponse response) {
		this.response = response;
	}

	public boolean isAddAmountInvoked() {
		return isAddAmountInvoked;
	}

	public boolean isTransferAmountInvoked() {
		return isTransferAmountInvoked;
	}

}
