package com.revolut.moneytransfer.repo.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.repo.IBalanceRepository;

public class BalanceRepository extends GenericRepository<IBalance, String> implements IBalanceRepository {

	@Override
	public List<IBalance> retrieveAccountBalance(String accountId) {
		return findAll().stream().filter(balance -> balance.getAccountId().equals(accountId))
				.collect(Collectors.toList());
	}

}
