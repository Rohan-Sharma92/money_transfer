package com.revolut.moneytransfer.repo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.revolut.moneytransfer.model.IAccount;
import com.revolut.moneytransfer.repo.IGenericRepository;

public class AccountRepository implements IGenericRepository<IAccount, String> {
	private final Map<String, IAccount> accountCache = new HashMap<>();

	@Override
	public IAccount findById(String id) {
		return accountCache.get(id);
	}

	@Override
	public List<IAccount> findAll() {
		return Lists.newArrayList(accountCache.values());
	}

	@Override
	public void addRecord(IAccount record) {
		accountCache.put(record.getId(), record);
	}

	@Override
	public void removeRecord(String id) {
		accountCache.remove(id);
	}

}
