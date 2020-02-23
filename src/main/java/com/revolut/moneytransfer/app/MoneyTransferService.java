package com.revolut.moneytransfer.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.moneytransfer.app.guice.MoneyTransferModule;
import com.revolut.moneytransfer.handlers.AccountRequestHandler;
import com.revolut.moneytransfer.handlers.MoneyTransferRequestHandler;
import com.revolut.moneytransfer.handlers.WalletRequestHandler;
import com.revolut.moneytransfer.util.Constants;

import spark.Spark;

public class MoneyTransferService {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MoneyTransferModule());
		AccountRequestHandler accountRequestHandler = injector.getInstance(AccountRequestHandler.class);
		WalletRequestHandler walletRequestHandler = injector.getInstance(WalletRequestHandler.class);
		MoneyTransferRequestHandler moneyTransferRequestHandler = injector
				.getInstance(MoneyTransferRequestHandler.class);
		Spark.post(Constants.INDIVIDUAL_ACCOUNT_PATHNAME, accountRequestHandler);
		Spark.get(Constants.INDIVIDUAL_ACCOUNT_PATHNAME, accountRequestHandler);
		Spark.get(Constants.ACCOUNTS_PATHNAME, accountRequestHandler);
		Spark.post(Constants.WALLET_PATHNAME, walletRequestHandler);
		Spark.get(Constants.WALLET_BALANCE_PATHNAME, walletRequestHandler);
		Spark.post(Constants.TRANSFER_PATH_NAME, moneyTransferRequestHandler);
	}
}
