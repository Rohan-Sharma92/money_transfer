package com.revolut.moneytransfer.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.moneytransfer.app.guice.MoneyTransferModule;
import com.revolut.moneytransfer.handlers.AccountRequestHandler;
import com.revolut.moneytransfer.handlers.WalletRequestHandler;
import com.revolut.moneytransfer.handlers.MoneyTransferRequestHandler;

import spark.Spark;

public class MoneyTransferService {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MoneyTransferModule());
		AccountRequestHandler route = injector.getInstance(AccountRequestHandler.class);
		WalletRequestHandler walletRequestHandler = injector.getInstance(WalletRequestHandler.class);
		MoneyTransferRequestHandler moneyTransferRequestHandler = injector
				.getInstance(MoneyTransferRequestHandler.class);
		Spark.post("/accounts/:id", route);
		Spark.get("/accounts/:id", route);
		Spark.get("/accounts", route);
		Spark.post("/wallet", walletRequestHandler);
		Spark.get("/wallet/:accountId", walletRequestHandler);
		Spark.post("/transfer", moneyTransferRequestHandler);
		/*
		 * 1.Create Retrieval Service 2. Create Balance Service 3. Create Balance repo
		 * 4. Create a transfer service 5. Create a transfer repo 6. Add validations 7.
		 * Add synchronization 8. Create tests data for FxRates 9. Add synschronization
		 * 10. Add guice 11. Add UTs 12. Add balance to a single account after adding
		 * account
		 */
	}
}
