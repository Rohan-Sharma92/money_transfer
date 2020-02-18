package com.revolut.moneytransfer.model;

public interface Identifiable<T> {

	T getId();
	
	void setId(T id);
}
