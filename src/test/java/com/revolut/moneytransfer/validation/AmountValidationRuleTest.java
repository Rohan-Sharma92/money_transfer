package com.revolut.moneytransfer.validation;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.revolut.moneytransfer.validation.impl.AmountValidationRule;

public class AmountValidationRuleTest {

	private AmountValidationRule amountValidationRule;

	@Before
	public void setup() {
		amountValidationRule = new AmountValidationRule();
	}

	@Test
	public void testValidationPasses() {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("amount", 500d);
		Assert.assertTrue(amountValidationRule.isValid(requestMap));
	}

	@Test
	public void testValidationFails() {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("amount", -500d);
		Assert.assertFalse(amountValidationRule.isValid(requestMap));
		Assert.assertEquals(amountValidationRule.getErrorMessage(), "amount is invalid.");
	}

	@Test
	public void testValidationIsNotApplied() {
		Map<String, Object> requestMap = new HashMap<>();
		Assert.assertTrue(amountValidationRule.isValid(requestMap));
	}
}
