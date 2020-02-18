package com.revolut.moneytransfer.handlers;

public enum StatusResponse {
	SUCCESS("Success"), ERROR("Error");

	private String statusStr;

	private StatusResponse(final String statusStr) {
		this.statusStr = statusStr;

	}

	public String getStatusStr() {
		return statusStr;
	}
}
