package com.revolut.moneytransfer.validation;

import javax.inject.Inject;

import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;

public class DebitAccountValidationRule extends AccountValidationRule {

	private static final String DEBIT_ACCOUNT = "debitAccount";

	@Inject
	public DebitAccountValidationRule(IGenericRepository<IAccount, String> accountRepo) {
		super(DEBIT_ACCOUNT, accountRepo);
	}

}
