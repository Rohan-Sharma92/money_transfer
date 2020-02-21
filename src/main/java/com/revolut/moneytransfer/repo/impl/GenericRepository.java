package com.revolut.moneytransfer.repo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.revolut.moneytransfer.model.Identifiable;
import com.revolut.moneytransfer.repo.IGenericRepository;

public class GenericRepository<T extends Identifiable<Y>, Y> implements IGenericRepository<T, Y> {

	private final Map<Y, T> cache = new HashMap<>();

	@Override
	public T findById(Y id) {
		return cache.get(id);
	}

	@Override
	public List<T> findAll() {
		return Lists.newArrayList(cache.values());
	}

	@Override
	public void addRecord(T record) {
		cache.put(record.getId(), record);
	}

	@Override
	public T removeRecord(Y id) {
		return cache.remove(id);
	}

}
