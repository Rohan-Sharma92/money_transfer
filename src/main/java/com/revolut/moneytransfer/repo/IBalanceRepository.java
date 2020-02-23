package com.revolut.moneytransfer.repo;

import java.util.List;

import com.revolut.moneytransfer.model.IBalance;

public interface IBalanceRepository extends IGenericRepository<IBalance,String>{

	List<IBalance> retrieveAccountBalance(String accountId);
}
