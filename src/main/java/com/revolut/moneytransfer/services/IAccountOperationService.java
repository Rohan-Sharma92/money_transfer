package com.revolut.moneytransfer.services;

import com.revolut.moneytransfer.handlers.StandardResponse;
import com.revolut.moneytransfer.model.IAccount;

public interface IAccountOperationService {

	StandardResponse addAccount(IAccount account);

	StandardResponse removeAccount(String id);

	StandardResponse retrieveAccount(String id);

	StandardResponse retrieveAccounts();
}
