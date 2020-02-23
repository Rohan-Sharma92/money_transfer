package com.revolut.moneytransfer.handlers;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revolut.moneytransfer.MockValidationRuleEngine;
import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.model.impl.Account;
import com.revolut.moneytransfer.model.impl.Balance;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.repo.impl.GenericRepository;
import com.revolut.moneytransfer.services.impl.MockMoneyTransferOperationService;
import com.revolut.moneytransfer.util.MoneyTransferUtil;

import spark.Request;
import spark.Response;

public class WalletRequestHandlerTest {

	private WalletRequestHandler walletRequestHandler;

	private MockMoneyTransferOperationService mockService;

	private MockValidationRuleEngine mockValidationEngine;

	private IGenericRepository<IAccount, String> accountRepo;

	@Before
	public void setup() {
		mockService = new MockMoneyTransferOperationService();
		mockValidationEngine = new MockValidationRuleEngine();
		accountRepo = new GenericRepository<>();
		walletRequestHandler = new WalletRequestHandler(mockService, accountRepo, mockValidationEngine);
	}

	@Test
	public void testAddMoneyIsSuccessful() throws Exception {
		Request request = Mockito.mock(Request.class);
		Response response = Mockito.mock(Response.class);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("accountId", "1");
		jsonObject.addProperty("amount", 500.0);
		jsonObject.addProperty("currency", Currency.EUR.toString());
		Mockito.when(request.body()).thenReturn(jsonObject.toString());
		Mockito.when(request.requestMethod()).thenReturn("POST");
		mockService.setExpectedResponse(new StandardResponse(StatusResponse.SUCCESS));
		Object object = walletRequestHandler.handle(request, response);
		StandardResponse actualResponse = new Gson().fromJson((String) object, StandardResponse.class);
		Assert.assertEquals(actualResponse.getStatus(), StatusResponse.SUCCESS);
		Assert.assertTrue(mockService.isAddAmountInvoked());
	}

	@Test
	public void testAddAmountIsNotProcessedWhenValidationFails() throws Exception {
		Request request = Mockito.mock(Request.class);
		Response response = Mockito.mock(Response.class);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("accountId", "1");
		jsonObject.addProperty("amount", 500.0);
		jsonObject.addProperty("currency", Currency.EUR.toString());
		Mockito.when(request.body()).thenReturn(jsonObject.toString());
		Mockito.when(request.requestMethod()).thenReturn("POST");
		mockValidationEngine.setExpectedErrorMessage("Invalid Details");
		Object object = walletRequestHandler.handle(request, response);
		StandardResponse actualResponse = new Gson().fromJson((String) object, StandardResponse.class);
		Assert.assertEquals(actualResponse.getStatus(), StatusResponse.ERROR);
		Assert.assertEquals(actualResponse.getMessage(), "Invalid Details");
		Assert.assertFalse(mockService.isAddAmountInvoked());
	}

	@Test
	public void testRetrieveBalance() throws Exception {
		Request request = Mockito.mock(Request.class);
		Response response = Mockito.mock(Response.class);
		IAccount account = new Account();
		account.setId("1");
		account.setName("Rohan");
		accountRepo.addRecord(account);
		IBalance balance = new Balance();
		balance.setAccountId(account.getId());
		balance.setAmount(BigDecimal.ONE);
		balance.setCurrency(Currency.EUR);
		balance.setId(MoneyTransferUtil.generateBalanceKey(account.getId(), Currency.EUR));
		Mockito.when(request.requestMethod()).thenReturn("GET");
		Mockito.when(request.params("id")).thenReturn(account.getId());
		StandardResponse standardResponse = new StandardResponse(StatusResponse.SUCCESS, "0:Ok",
				new Gson().toJsonTree(balance));
		mockService.setExpectedResponse(standardResponse);
		Object object = walletRequestHandler.handle(request, response);
		StandardResponse actualResponse = new Gson().fromJson((String) object, StandardResponse.class);
		Assert.assertEquals(actualResponse.getStatus(), StatusResponse.SUCCESS);
		IBalance balances = new Gson().fromJson(actualResponse.getElement(), Balance.class);
		Assert.assertEquals(balances.getAccountId(), account.getId());
	}
}
