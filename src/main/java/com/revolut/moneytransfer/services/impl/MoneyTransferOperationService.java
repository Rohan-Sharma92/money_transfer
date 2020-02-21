package com.revolut.moneytransfer.services.impl;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.revolut.moneytransfer.handlers.StandardResponse;
import com.revolut.moneytransfer.handlers.StatusResponse;
import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.model.impl.Balance;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.services.IMoneyTransferOperationService;

public class MoneyTransferOperationService implements IMoneyTransferOperationService {

	private final IGenericRepository<IBalance, String> balanceCache;

	@Inject
	public MoneyTransferOperationService(final IGenericRepository<IBalance, String> balanceCache) {
		this.balanceCache = balanceCache;
	}

	@Override
	public StandardResponse addAmount(IAccount account, BigDecimal amount, Currency currency) {
		// TODO
		/*
		 * 1. Add validation 2. add amount logic 3. update balance and send response
		 */
		IBalance balance = balanceCache.findById(generateBalanceKey(account, currency));
		if (balance == null) {
			balance = new Balance();
			balance.setId(generateBalanceKey(account, currency));
			balance.setAccountId(account.getId());
			balance.setAmount(amount);
			balance.setCurrency(currency);
			balanceCache.addRecord(balance);
		} else {
			balance.setAmount(balance.getAmount().add(amount));
		}
		return new StandardResponse(StatusResponse.SUCCESS, "0:0k", new Gson().toJsonTree(balance));
	}

	private String generateBalanceKey(IAccount account, Currency currency) {
		return StringUtils.join(new String[] { account.getId(), currency.getDisplayString() }, '_');
	}

	@Override
	public StandardResponse transferAmount(IAccount creditAccount, IAccount debitAccount, BigDecimal amount,
			Currency currency) {
		// TODO
		/*
		 * 1. Add validation 2. transfer amount logic 3. update balance and send
		 * response
		 */
		IBalance debitBalance = balanceCache.findById(generateBalanceKey(debitAccount, currency));
		debitBalance.setAmount(debitBalance.getAmount().subtract(amount));

		IBalance creditBalance = balanceCache.findById(generateBalanceKey(creditAccount, currency));
		if (creditBalance != null) {
			creditBalance.setAmount(creditBalance.getAmount().add(amount));
		} else {
			addAmount(creditAccount, amount, currency);
			creditBalance = balanceCache.findById(generateBalanceKey(creditAccount, currency));
		}
		return new StandardResponse(StatusResponse.SUCCESS, "0:0k",
				new Gson().toJsonTree(Lists.newArrayList(debitBalance, creditBalance)));
	}

}
