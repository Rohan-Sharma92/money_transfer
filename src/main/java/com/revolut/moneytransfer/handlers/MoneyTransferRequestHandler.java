package com.revolut.moneytransfer.handlers;

import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.requestwrappers.TransferRequestWrapper;
import com.revolut.moneytransfer.services.IMoneyTransferOperationService;
import com.revolut.moneytransfer.validation.IValidationRuleEngine;

import spark.Request;
import spark.Response;
import spark.Route;

public class MoneyTransferRequestHandler implements Route {

	private final IMoneyTransferOperationService moneyTransferOperationService;
	private final IValidationRuleEngine validationRuleEngine;
	private final IGenericRepository<IAccount, String> accountRepo;

	@Inject
	public MoneyTransferRequestHandler(final IMoneyTransferOperationService moneyTransferOperationService,
			final IValidationRuleEngine validationRuleEngine, final IGenericRepository<IAccount, String> accountRepo) {
		this.moneyTransferOperationService = moneyTransferOperationService;
		this.validationRuleEngine = validationRuleEngine;
		this.accountRepo = accountRepo;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.type("application/json");
		Map<String, Object> requestMap = new Gson().fromJson(request.body(), Map.class);
		String validateMessage = validationRuleEngine.validate(requestMap);
		if (StringUtils.isEmpty(validateMessage)) {
			TransferRequestWrapper transferDetails = new Gson().fromJson(request.body(), TransferRequestWrapper.class);
			IAccount creditAccount = accountRepo.findById(transferDetails.getAccountId());
			IAccount debitAccount = accountRepo.findById(transferDetails.getDebitAccount());
			StandardResponse standardResponse = moneyTransferOperationService.transferAmount(creditAccount,
					debitAccount, transferDetails.getAmount(), transferDetails.getCurrency());
			return new Gson().toJson(standardResponse);
		}
		return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, validateMessage));
	}

}
