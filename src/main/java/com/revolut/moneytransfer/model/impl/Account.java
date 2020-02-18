package com.revolut.moneytransfer.model.impl;

import com.revolut.moneytransfer.model.IAccount;

public class Account implements IAccount {

	private String id;
	private String name;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
