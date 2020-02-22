package com.revolut.moneytransfer.validation;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ValidationRuleEngine implements IValidationRuleEngine {

	private final List<IValidationRule> rules;

	@Inject
	public ValidationRuleEngine(final List<IValidationRule> rules) {
		this.rules = rules;
	}

	@Override
	public String validate(Map<String, Object> requestMap) {
		StringBuilder message = new StringBuilder();
		rules.stream().anyMatch(rule -> {
			if (!rule.isValid(requestMap)) {
				message.append(rule.getErrorMessage());
				return true;
			}
			return false;
		});
		return message.toString();
	}

}
