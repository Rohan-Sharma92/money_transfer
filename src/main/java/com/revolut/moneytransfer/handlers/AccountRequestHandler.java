package com.revolut.moneytransfer.handlers;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.model.impl.Account;
import com.revolut.moneytransfer.services.IAccountOperationService;
import com.revolut.moneytransfer.util.Constants;

import spark.Request;
import spark.Response;
import spark.Route;

public class AccountRequestHandler implements Route {

	private final IAccountOperationService accountOperationService;

	@Inject
	public AccountRequestHandler(final IAccountOperationService accountOperationService) {
		this.accountOperationService = accountOperationService;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		String requestMethod = request.requestMethod();
		switch (requestMethod) {
		case Constants.POST:
			IAccount account = new Gson().fromJson(request.body(), Account.class);
			StandardResponse standardResponse = accountOperationService.addAccount(account);
			return new Gson().toJson(standardResponse);
		case Constants.GET:
			return processRetrieval(request, response);
		default:
			return null;
		}

	}

	private Object processRetrieval(Request request, Response response) {
		String params = request.params(Constants.ID_PARAM);
		if (params == null) {
			return new Gson().toJson(accountOperationService.retrieveAccounts());
		}
		return new Gson().toJson(accountOperationService.retrieveAccount(params));
	}

}
