package com.revolut.moneytransfer.validation;

import java.util.Map;

public interface IValidationRule {

	boolean isValid(Map<String,Object> requestMap);
	
	String getErrorMessage();
}
