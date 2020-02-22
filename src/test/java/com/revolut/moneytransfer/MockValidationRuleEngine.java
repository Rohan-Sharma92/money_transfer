package com.revolut.moneytransfer;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.revolut.moneytransfer.validation.IValidationRuleEngine;

public class MockValidationRuleEngine implements IValidationRuleEngine {

	private String errorMessage=StringUtils.EMPTY;

	@Override
	public String validate(Map<String, Object> request) {
		return errorMessage;
	}

	public void setExpectedErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
