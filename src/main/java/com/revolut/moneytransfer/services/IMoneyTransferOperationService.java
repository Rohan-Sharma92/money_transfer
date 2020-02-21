package com.revolut.moneytransfer.services;

import java.math.BigDecimal;

import com.revolut.moneytransfer.handlers.StandardResponse;
import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IAccount;

public interface IMoneyTransferOperationService {

	StandardResponse addAmount(IAccount account, BigDecimal amount, Currency currency);

	StandardResponse transferAmount(IAccount creditAccount, IAccount debitAccount, BigDecimal amount,
			Currency currency);
}
