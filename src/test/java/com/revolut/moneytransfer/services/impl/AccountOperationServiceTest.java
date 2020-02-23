package com.revolut.moneytransfer.services.impl;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revolut.moneytransfer.handlers.StandardResponse;
import com.revolut.moneytransfer.handlers.StatusResponse;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.impl.Account;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.repo.impl.GenericRepository;

public class AccountOperationServiceTest {

	private AccountOperationService accountOperationService;
	private IGenericRepository<IAccount, String> accountRepo;

	@Before
	public void setup() {
		accountRepo = new GenericRepository<IAccount, String>();
		accountOperationService = new AccountOperationService(accountRepo);
	}

	@Test
	public void testAddAccountService() {
		IAccount account = new Account();
		account.setId("1");
		account.setName("Rohan");
		StandardResponse response = accountOperationService.addAccount(account);
		Assert.assertEquals(accountRepo.findById(account.getId()), account);
		Assert.assertEquals(response.getStatus(), StatusResponse.SUCCESS);
	}

	@Test
	public void testRemoveAccountService() {
		IAccount account = new Account();
		account.setId("1");
		account.setName("Rohan");
		accountOperationService.addAccount(account);
		StandardResponse response = accountOperationService.removeAccount(account.getId());
		Assert.assertNull(accountRepo.findById(account.getId()));
		Assert.assertEquals(response.getStatus(), StatusResponse.SUCCESS);
	}

	@Test
	public void testRetrieveAccountService() {
		IAccount account = new Account();
		account.setId("1");
		account.setName("Rohan");
		IAccount account2 = new Account();
		account2.setId("2");
		account.setName("Rahul");
		accountOperationService.addAccount(account);
		accountOperationService.addAccount(account2);
		StandardResponse response = accountOperationService.retrieveAccount(account.getId());
		IAccount retrievedAccount = new Gson().fromJson(response.getElement(), Account.class);
		Assert.assertEquals(response.getStatus(), StatusResponse.SUCCESS);
		Assert.assertEquals(retrievedAccount.getId(), account.getId());
	}

	@Test
	public void testRetrieveAccountsService() {
		IAccount account = new Account();
		account.setId("1");
		account.setName("Rohan");
		IAccount account2 = new Account();
		account2.setId("2");
		account.setName("Rahul");
		accountOperationService.addAccount(account);
		accountOperationService.addAccount(account2);
		StandardResponse response = accountOperationService.retrieveAccounts();
		List<IAccount> retrievedAccounts = new Gson().fromJson(response.getElement(), new TypeToken<List<Account>>() {
		}.getType());
		Assert.assertEquals(response.getStatus(), StatusResponse.SUCCESS);
		Assert.assertEquals(retrievedAccounts.size(), 2);
	}
}
