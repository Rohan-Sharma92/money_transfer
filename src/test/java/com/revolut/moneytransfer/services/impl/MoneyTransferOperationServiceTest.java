package com.revolut.moneytransfer.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.model.impl.Account;
import com.revolut.moneytransfer.model.impl.Balance;
import com.revolut.moneytransfer.repo.IBalanceRepository;
import com.revolut.moneytransfer.repo.impl.BalanceRepository;
import com.revolut.moneytransfer.util.MoneyTransferUtil;

public class MoneyTransferOperationServiceTest {

	private MoneyTransferOperationService moneyTransferOperationService;
	private IBalanceRepository balanceCache;

	@Before
	public void setup() {
		balanceCache = new BalanceRepository();
		moneyTransferOperationService = new MoneyTransferOperationService(balanceCache);
	}

	@Test
	public void testAddAmountCreatesANewBalance() {
		IAccount account = new Account();
		account.setId("1");
		account.setName("Rohan");
		moneyTransferOperationService.addAmount(account, BigDecimal.TEN, Currency.EUR);
		List<IBalance> accountBalance = balanceCache.retrieveAccountBalance(account.getId());
		Assert.assertEquals(accountBalance.size(), 1);
		Assert.assertEquals(accountBalance.get(0).getAccountId(), account.getId());
		Assert.assertEquals(accountBalance.get(0).getAmount(), BigDecimal.TEN);

	}

	@Test
	public void testAddAmountUpdatesANewBalance() {
		IAccount account = new Account();
		account.setId("1");
		account.setName("Rohan");
		IBalance balance = new Balance();
		balance.setAccountId(account.getId());
		balance.setAmount(BigDecimal.ONE);
		balance.setCurrency(Currency.EUR);
		balance.setId(MoneyTransferUtil.generateBalanceKey(account.getId(), Currency.EUR));
		balanceCache.addRecord(balance);
		moneyTransferOperationService.addAmount(account, BigDecimal.TEN, Currency.EUR);
		List<IBalance> accountBalance = balanceCache.retrieveAccountBalance(account.getId());
		Assert.assertEquals(accountBalance.size(), 1);
		Assert.assertEquals(accountBalance.get(0).getAccountId(), account.getId());
		Assert.assertEquals(accountBalance.get(0).getAmount(), new BigDecimal(11));
	}

	@Test
	public void testTransferAmountIsSuccessful() {
		IAccount debitAccount = new Account();
		debitAccount.setId("1");
		debitAccount.setName("Rohan");
		IBalance balance = new Balance();
		balance.setAccountId(debitAccount.getId());
		balance.setAmount(BigDecimal.TEN);
		balance.setCurrency(Currency.EUR);
		balance.setId(MoneyTransferUtil.generateBalanceKey(debitAccount.getId(), Currency.EUR));
		balanceCache.addRecord(balance);
		IAccount creditAccount = new Account();
		creditAccount.setId("2");
		creditAccount.setName("Rahul");
		moneyTransferOperationService.transferAmount(creditAccount, debitAccount, BigDecimal.TEN, Currency.EUR);
		List<IBalance> debitAccountBalance = balanceCache.retrieveAccountBalance(debitAccount.getId());
		List<IBalance> creditAccountBalance = balanceCache.retrieveAccountBalance(creditAccount.getId());
		Assert.assertEquals(debitAccountBalance.get(0).getAccountId(), debitAccount.getId());
		Assert.assertEquals(debitAccountBalance.get(0).getAmount(), BigDecimal.ZERO);
		Assert.assertEquals(creditAccountBalance.get(0).getAccountId(), creditAccount.getId());
		Assert.assertEquals(creditAccountBalance.get(0).getAmount(), BigDecimal.TEN);
	}

	@Test
	public void testTransferAmountUpdatesExixtingBalance() {
		IAccount debitAccount = new Account();
		debitAccount.setId("1");
		debitAccount.setName("Rohan");
		IBalance debitBalance = new Balance();
		debitBalance.setAccountId(debitAccount.getId());
		debitBalance.setAmount(BigDecimal.TEN);
		debitBalance.setCurrency(Currency.EUR);
		debitBalance.setId(MoneyTransferUtil.generateBalanceKey(debitAccount.getId(), Currency.EUR));
		balanceCache.addRecord(debitBalance);
		IAccount creditAccount = new Account();
		creditAccount.setId("2");
		creditAccount.setName("Rahul");
		IBalance creditBalance = new Balance();
		creditBalance.setAccountId(creditAccount.getId());
		creditBalance.setAmount(BigDecimal.ONE);
		creditBalance.setCurrency(Currency.EUR);
		creditBalance.setId(MoneyTransferUtil.generateBalanceKey(creditAccount.getId(), Currency.EUR));
		balanceCache.addRecord(creditBalance);
		moneyTransferOperationService.transferAmount(creditAccount, debitAccount, BigDecimal.TEN, Currency.EUR);
		List<IBalance> debitAccountBalance = balanceCache.retrieveAccountBalance(debitAccount.getId());
		List<IBalance> creditAccountBalance = balanceCache.retrieveAccountBalance(creditAccount.getId());
		Assert.assertEquals(debitAccountBalance.get(0).getAccountId(), debitAccount.getId());
		Assert.assertEquals(debitAccountBalance.get(0).getAmount(), BigDecimal.ZERO);
		Assert.assertEquals(creditAccountBalance.get(0).getAccountId(), creditAccount.getId());
		Assert.assertEquals(creditAccountBalance.get(0).getAmount(), new BigDecimal(11));
	}
}
