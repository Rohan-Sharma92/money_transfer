package com.revolut.moneytransfer.handlers;

import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.requestwrappers.WalletRequestWrapper;
import com.revolut.moneytransfer.services.IMoneyTransferOperationService;
import com.revolut.moneytransfer.validation.IValidationRuleEngine;

import spark.Request;
import spark.Response;
import spark.Route;

public class WalletRequestHandler implements Route {

	private final IMoneyTransferOperationService moneyTransferOperationService;
	private final IGenericRepository<IAccount, String> accountRepo;
	private final IValidationRuleEngine validationRuleEngine;

	@Inject
	public WalletRequestHandler(final IMoneyTransferOperationService moneyTransferOperationService,
			final IGenericRepository<IAccount, String> accountRepo, final IValidationRuleEngine validationRuleEngine) {
		this.moneyTransferOperationService = moneyTransferOperationService;
		this.accountRepo = accountRepo;
		this.validationRuleEngine = validationRuleEngine;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		String requestMethod = request.requestMethod();
		switch (requestMethod) {
		case "POST":
			Map<String, Object> requestMap = new Gson().fromJson(request.body(), Map.class);
			String validateMessage = validationRuleEngine.validate(requestMap);
			if (StringUtils.isEmpty(validateMessage)) {
				WalletRequestWrapper walletDetails = new Gson().fromJson(request.body(), WalletRequestWrapper.class);
				IAccount account = accountRepo.findById(walletDetails.getAccountId());
				StandardResponse standardResponse = moneyTransferOperationService.addAmount(account,
						walletDetails.getAmount(), walletDetails.getCurrency());
				return new Gson().toJson(standardResponse);
			}
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, validateMessage));
		case "GET":
			String accountId = request.params("id");
			IAccount account = accountRepo.findById(accountId);
			if (account == null) {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR));
			}
			return new Gson().toJson(moneyTransferOperationService.retrieveBalance(account));
		default:
			return null;
		}
	}

}
