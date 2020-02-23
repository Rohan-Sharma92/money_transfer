package com.revolut.moneytransfer.validation.impl;

import java.util.Map;

import javax.inject.Inject;

import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.util.Constants;
import com.revolut.moneytransfer.validation.IValidationRule;

public class AccountValidationRule implements IValidationRule {

	private final IGenericRepository<IAccount, String> accountRepo;
	private final String fieldName;

	@Inject
	public AccountValidationRule(final IGenericRepository<IAccount, String> accountRepo) {
		this(Constants.ACCOUNT_ID_PARAM, accountRepo);
	}

	public AccountValidationRule(final String fieldName, final IGenericRepository<IAccount, String> accountRepo) {
		this.fieldName = fieldName;
		this.accountRepo = accountRepo;
	}

	@Override
	public boolean isValid(final Map<String, Object> requestMap) {
		boolean isValid = true;
		if (requestMap.containsKey(fieldName))
			return accountRepo.findById(requestMap.get(fieldName).toString()) != null;
		return isValid;
	}

	@Override
	public String getErrorMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append(fieldName).append(Constants.IS_INVALID);
		return builder.toString();
	}

}
