package com.revolut.moneytransfer.services;

import com.revolut.moneytransfer.model.IAccount;

public interface IAccountOperationService {

	void addAccount(IAccount account);
	
	void removeAccount(String id);
}
