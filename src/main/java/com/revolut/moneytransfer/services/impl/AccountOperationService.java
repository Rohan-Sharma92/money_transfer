package com.revolut.moneytransfer.services.impl;

import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.services.IAccountOperationService;

public class AccountOperationService implements IAccountOperationService {

	private final IGenericRepository<IAccount, String> accountRepo;

	public AccountOperationService(final IGenericRepository<IAccount, String> accountRepo) {
		this.accountRepo = accountRepo;
	}

	@Override
	public void addAccount(final IAccount account) {
		accountRepo.addRecord(account);
	}

	@Override
	public void removeAccount(final String id) {
		accountRepo.removeRecord(id);
	}

}
