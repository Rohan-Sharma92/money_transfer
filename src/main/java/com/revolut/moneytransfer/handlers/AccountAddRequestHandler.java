package com.revolut.moneytransfer.handlers;

import com.google.gson.Gson;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.impl.Account;
import com.revolut.moneytransfer.services.IAccountOperationService;

import spark.Request;
import spark.Response;
import spark.Route;

public class AccountAddRequestHandler implements Route {

	private final IAccountOperationService accountOperationService;

	public AccountAddRequestHandler(final IAccountOperationService accountOperationService) {
		this.accountOperationService = accountOperationService;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		IAccount account = new Gson().fromJson(request.body(), Account.class);
		accountOperationService.addAccount(account);
		return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
	}

}
