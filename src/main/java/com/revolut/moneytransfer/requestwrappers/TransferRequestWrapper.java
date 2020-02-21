package com.revolut.moneytransfer.requestwrappers;

public class TransferRequestWrapper extends WalletRequestWrapper {

	private String debitAccount;

	public String getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}
}
