package com.revolut.moneytransfer.handlers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revolut.moneytransfer.MockValidationRuleEngine;
import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.repo.impl.GenericRepository;
import com.revolut.moneytransfer.services.impl.MockMoneyTransferOperationService;

import spark.Request;
import spark.Response;

public class MoneyTransferRequestHandlerTest {

	private MoneyTransferRequestHandler moneyTransferRequestHandler;

	private MockMoneyTransferOperationService mockService;

	private MockValidationRuleEngine mockValidationEngine;

	private IGenericRepository<IAccount, String> accountRepo;

	@Before
	public void setup() {
		mockService = new MockMoneyTransferOperationService();
		mockValidationEngine = new MockValidationRuleEngine();
		accountRepo = new GenericRepository<>();
		moneyTransferRequestHandler = new MoneyTransferRequestHandler(mockService, mockValidationEngine, accountRepo);
	}

	@Test
	public void testTransferIsSuccessful() throws Exception {
		Request request = Mockito.mock(Request.class);
		Response response = Mockito.mock(Response.class);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("accountId", "1");
		jsonObject.addProperty("amount", 500.0);
		jsonObject.addProperty("debitAccount", "2");
		jsonObject.addProperty("currency", Currency.EUR.toString());
		Mockito.when(request.body()).thenReturn(jsonObject.toString());
		mockService.setExpectedResponse(new StandardResponse(StatusResponse.SUCCESS));
		Object object = moneyTransferRequestHandler.handle(request, response);
		StandardResponse actualResponse = new Gson().fromJson((String) object, StandardResponse.class);
		Assert.assertEquals(actualResponse.getStatus(), StatusResponse.SUCCESS);
		Assert.assertTrue(mockService.isTransferAmountInvoked());
	}

	@Test
	public void testTransferIsNotProcessedWhenValidationFails() throws Exception {
		Request request = Mockito.mock(Request.class);
		Response response = Mockito.mock(Response.class);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("accountId", "1");
		jsonObject.addProperty("amount", 500.0);
		jsonObject.addProperty("debitAccount", "2");
		jsonObject.addProperty("currency", Currency.EUR.toString());
		Mockito.when(request.body()).thenReturn(jsonObject.toString());
		mockValidationEngine.setExpectedErrorMessage("Invalid Details");
		Object object = moneyTransferRequestHandler.handle(request, response);
		StandardResponse actualResponse = new Gson().fromJson((String) object, StandardResponse.class);
		Assert.assertEquals(actualResponse.getStatus(), StatusResponse.ERROR);
		Assert.assertEquals(actualResponse.getMessage(), "Invalid Details");
		Assert.assertFalse(mockService.isTransferAmountInvoked());
	}
}
