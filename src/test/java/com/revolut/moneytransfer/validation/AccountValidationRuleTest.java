package com.revolut.moneytransfer.validation;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.impl.Account;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.repo.impl.GenericRepository;
import com.revolut.moneytransfer.validation.impl.AccountValidationRule;

public class AccountValidationRuleTest {

	private AccountValidationRule accountValidationRule;
	private IGenericRepository<IAccount, String> accountRepo;

	@Before
	public void setup() {
		accountRepo = new GenericRepository<>();
		accountValidationRule = new AccountValidationRule(accountRepo);
	}

	@Test
	public void testValidationPasses() {
		IAccount record = new Account();
		record.setId("1");
		record.setName("Rohan");
		accountRepo.addRecord(record);
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("accountId", "1");
		Assert.assertTrue(accountValidationRule.isValid(requestMap));
	}

	@Test
	public void testValidationFails() {
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("accountId", "1");
		Assert.assertFalse(accountValidationRule.isValid(requestMap));
		Assert.assertEquals(accountValidationRule.getErrorMessage(), "accountId is invalid.");
	}

	@Test
	public void testValidationIsNotApplied() {
		Map<String, Object> requestMap = new HashMap<>();
		Assert.assertTrue(accountValidationRule.isValid(requestMap));
	}
}
