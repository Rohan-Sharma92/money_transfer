package com.revolut.moneytransfer.validation;

import java.util.Map;

public interface IValidationRuleEngine {

	String validate(Map<String, Object> request);
}
