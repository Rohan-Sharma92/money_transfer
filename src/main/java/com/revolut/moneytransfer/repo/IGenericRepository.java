package com.revolut.moneytransfer.repo;

import java.util.List;

public interface IGenericRepository<T,Y> {

	T findById(Y id);
	
	List<T> findAll();
	
	void addRecord(T record);
	
	T removeRecord(Y id);
}
