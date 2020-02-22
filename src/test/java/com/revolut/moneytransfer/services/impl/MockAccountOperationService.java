package com.revolut.moneytransfer.services.impl;

import com.revolut.moneytransfer.handlers.StandardResponse;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.services.IAccountOperationService;

public class MockAccountOperationService implements IAccountOperationService {

	private StandardResponse response;
	private boolean isAddAccountInvoked;
	private boolean isRetrieveAccountInvoked;
	private boolean isRetrieveAccountsInvoked;

	@Override
	public StandardResponse addAccount(IAccount account) {
		this.isAddAccountInvoked=(true);
		return response;
	}

	@Override
	public StandardResponse removeAccount(String id) {
		return response;
	}

	@Override
	public StandardResponse retrieveAccount(String id) {
		this.isRetrieveAccountInvoked=true;
		return response;
	}

	@Override
	public StandardResponse retrieveAccounts() {
		this.isRetrieveAccountsInvoked=true;
		return response;
	}

	public void setExpectedResponse(StandardResponse response) {
		this.response = response;
	}

	public boolean isAddAccountInvoked() {
		return isAddAccountInvoked;
	}

	public boolean isRetrieveAccountInvoked() {
		return isRetrieveAccountInvoked;
	}

	public boolean isRetrieveAccountsInvoked() {
		return isRetrieveAccountsInvoked;
	}

}
