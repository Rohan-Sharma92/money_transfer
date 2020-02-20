package com.revolut.moneytransfer.app;

import com.revolut.moneytransfer.handlers.AccountAddRequestHandler;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.repo.impl.GenericRepository;
import com.revolut.moneytransfer.services.IAccountOperationService;
import com.revolut.moneytransfer.services.impl.AccountOperationService;

import spark.Spark;

public class MoneyTransferService {

	public static void main(String[] args) {
		IGenericRepository<IAccount,String> accountRepo = new GenericRepository<>();
		IAccountOperationService accountOperationService = new AccountOperationService(accountRepo);
		AccountAddRequestHandler route = new AccountAddRequestHandler(accountOperationService);
		Spark.post("/accounts/:id", route);
		Spark.get("/accounts/:id", route);
		Spark.get("/accounts", route);
		/* 1.Create Retrieval Service
		 * 2. Create Balance Service
		 * 3. Create Balance repo
		 * 4. Create a transfer service
		 * 5. Create a transfer repo
		 * 6. Add validations
		 * 7. Add synchronization
		 * 8. Create tests data for FxRates
		 * 9. Add synschronization
		 * 10. Add guice
		 * 11. Add UTs
		 * 12. Add balance to a single account after adding account
		 */
	}
}
