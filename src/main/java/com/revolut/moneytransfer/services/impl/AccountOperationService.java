package com.revolut.moneytransfer.services.impl;

import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.revolut.moneytransfer.handlers.StandardResponse;
import com.revolut.moneytransfer.handlers.StatusResponse;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.services.IAccountOperationService;

public class AccountOperationService implements IAccountOperationService {

	private final IGenericRepository<IAccount, String> accountRepo;

	@Inject
	public AccountOperationService(final IGenericRepository<IAccount, String> accountRepo) {
		this.accountRepo = accountRepo;
	}

	@Override
	public StandardResponse addAccount(final IAccount account) {
		accountRepo.addRecord(account);
		return new StandardResponse(StatusResponse.SUCCESS, "0:0k", new Gson().toJsonTree(account));
	}

	@Override
	public StandardResponse removeAccount(final String id) {
		IAccount removeRecord = accountRepo.removeRecord(id);
		return new StandardResponse(StatusResponse.SUCCESS, "0:0k", new Gson().toJsonTree(removeRecord));
	}

	@Override
	public StandardResponse retrieveAccount(String id) {
		IAccount account = accountRepo.findById(id);
		if (account == null) {
			return new StandardResponse(StatusResponse.ERROR);
		}
		return new StandardResponse(StatusResponse.SUCCESS, "0:0k", new Gson().toJsonTree(account));
	}

	@Override
	public StandardResponse retrieveAccounts() {
		List<IAccount> accounts = accountRepo.findAll();
		return new StandardResponse(StatusResponse.SUCCESS, "0:0k", new Gson().toJsonTree(accounts));
	}

}
