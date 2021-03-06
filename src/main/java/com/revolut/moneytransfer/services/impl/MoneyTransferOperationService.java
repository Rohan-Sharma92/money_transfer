package com.revolut.moneytransfer.services.impl;

import java.math.BigDecimal;

import javax.inject.Inject;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.revolut.moneytransfer.handlers.StandardResponse;
import com.revolut.moneytransfer.handlers.StatusResponse;
import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.model.impl.Balance;
import com.revolut.moneytransfer.repo.IBalanceRepository;
import com.revolut.moneytransfer.services.IMoneyTransferOperationService;
import com.revolut.moneytransfer.util.Constants;
import com.revolut.moneytransfer.util.MoneyTransferUtil;

public class MoneyTransferOperationService implements IMoneyTransferOperationService {

	private final IBalanceRepository balanceCache;

	@Inject
	public MoneyTransferOperationService(final IBalanceRepository balanceCache) {
		this.balanceCache = balanceCache;
	}

	@Override
	public StandardResponse addAmount(IAccount account, BigDecimal amount, Currency currency) {
		IBalance balance = balanceCache.findById(MoneyTransferUtil.generateBalanceKey(account.getId(), currency));
		if (balance == null) {
			balance = generateBalance(account, amount, currency);
			balanceCache.addRecord(balance);
		} else {
			balance.setAmount(balance.getAmount().add(amount));
		}
		return new StandardResponse(StatusResponse.SUCCESS, Constants.SUCCESS_CODE, new Gson().toJsonTree(balance));
	}

	private IBalance generateBalance(IAccount account, BigDecimal amount, Currency currency) {
		IBalance balance;
		balance = new Balance();
		balance.setId(MoneyTransferUtil.generateBalanceKey(account.getId(), currency));
		balance.setAccountId(account.getId());
		balance.setAmount(amount);
		balance.setCurrency(currency);
		return balance;
	}

	@Override
	public StandardResponse transferAmount(IAccount creditAccount, IAccount debitAccount, BigDecimal amount,
			Currency currency) {
		IBalance debitBalance = balanceCache
				.findById(MoneyTransferUtil.generateBalanceKey(debitAccount.getId(), currency));
		BigDecimal expectedDebitBalance = debitBalance.getAmount().subtract(amount);
		debitBalance.setAmount(expectedDebitBalance);

		IBalance creditBalance = balanceCache
				.findById(MoneyTransferUtil.generateBalanceKey(creditAccount.getId(), currency));
		if (creditBalance != null) {
			creditBalance.setAmount(creditBalance.getAmount().add(amount));
		} else {
			addAmount(creditAccount, amount, currency);
			creditBalance = balanceCache
					.findById(MoneyTransferUtil.generateBalanceKey(creditAccount.getId(), currency));
		}
		return new StandardResponse(StatusResponse.SUCCESS, Constants.SUCCESS_CODE,
				new Gson().toJsonTree(Lists.newArrayList(debitBalance, creditBalance)));
	}

	@Override
	public StandardResponse retrieveBalance(IAccount account) {
		return new StandardResponse(StatusResponse.SUCCESS, Constants.SUCCESS_CODE,
				new Gson().toJsonTree(balanceCache.retrieveAccountBalance(account.getId())));
	}

}
