package com.revolut.moneytransfer.model;

public interface IAccount extends Identifiable<String>{

	String getName();
	
	void setName(String name);
}
