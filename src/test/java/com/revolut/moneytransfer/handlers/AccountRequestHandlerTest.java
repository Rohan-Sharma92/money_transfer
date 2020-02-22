package com.revolut.moneytransfer.handlers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revolut.moneytransfer.services.impl.MockAccountOperationService;

import spark.Request;
import spark.Response;

public class AccountRequestHandlerTest {

	private AccountRequestHandler accountAddRequestHandler;
	private MockAccountOperationService accountOperationService;

	@Before
	public void setup() {
		accountOperationService = new MockAccountOperationService();
		accountAddRequestHandler = new AccountRequestHandler(accountOperationService);
	}

	@Test
	public void testAddAccountRequest() throws Exception {
		Request mockRequest = Mockito.mock(Request.class);
		Mockito.when(mockRequest.requestMethod()).thenReturn("POST");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", "1");
		jsonObject.addProperty("name", "Rohan");
		Mockito.when(mockRequest.body()).thenReturn(jsonObject.toString());
		Response mockResponse = Mockito.mock(Response.class);
		accountOperationService.setExpectedResponse(new StandardResponse(StatusResponse.SUCCESS));
		Object object = accountAddRequestHandler.handle(mockRequest, mockResponse);
		StandardResponse actualResponse = new Gson().fromJson((String) object, StandardResponse.class);
		Assert.assertEquals(actualResponse.getStatus(), StatusResponse.SUCCESS);
		Assert.assertTrue(accountOperationService.isAddAccountInvoked());
	}

	@Test
	public void testRetrieveAccountRequest() throws Exception {
		Request mockRequest = Mockito.mock(Request.class);
		Mockito.when(mockRequest.requestMethod()).thenReturn("GET");
		Mockito.when(mockRequest.params("id")).thenReturn("1");
		Response mockResponse = Mockito.mock(Response.class);
		accountOperationService.setExpectedResponse(new StandardResponse(StatusResponse.SUCCESS));
		Object object = accountAddRequestHandler.handle(mockRequest, mockResponse);
		StandardResponse actualResponse = new Gson().fromJson((String) object, StandardResponse.class);
		Assert.assertEquals(actualResponse.getStatus(), StatusResponse.SUCCESS);
		Assert.assertTrue(accountOperationService.isRetrieveAccountInvoked());
	}
	
	@Test
	public void testRetrieveAllAccountRequest() throws Exception {
		Request mockRequest = Mockito.mock(Request.class);
		Mockito.when(mockRequest.requestMethod()).thenReturn("GET");
		Response mockResponse = Mockito.mock(Response.class);
		accountOperationService.setExpectedResponse(new StandardResponse(StatusResponse.SUCCESS));
		Object object = accountAddRequestHandler.handle(mockRequest, mockResponse);
		StandardResponse actualResponse = new Gson().fromJson((String) object, StandardResponse.class);
		Assert.assertEquals(actualResponse.getStatus(), StatusResponse.SUCCESS);
		Assert.assertTrue(accountOperationService.isRetrieveAccountsInvoked());
	}
}
