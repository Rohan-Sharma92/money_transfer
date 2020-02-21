package com.revolut.moneytransfer.handlers;

import com.google.gson.JsonElement;

public class StandardResponse {

	private String message;

	private StatusResponse status;

	private JsonElement element;

	public StandardResponse(final StatusResponse statusResponse) {
		status = statusResponse;
		message =statusResponse.getStatusStr();
	}

	public StandardResponse(final StatusResponse statusResponse, final String message) {
		status = statusResponse;
		this.message = message;
	}
	
	public StandardResponse(final StatusResponse statusResponse, final String message,final JsonElement data) {
		status = statusResponse;
		this.message = message;
		element = data;
	}

	public String getMessage() {
		return message;
	}

	public StatusResponse getStatus() {
		return status;
	}

	public JsonElement getElement() {
		return element;
	}

}
