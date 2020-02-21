package com.revolut.moneytransfer.validation;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

public class ValidationRuleEngine implements IValidationRuleEngine {

	private final Set<IValidationRule> rules;

	@Inject
	public ValidationRuleEngine(final Set<IValidationRule> rules) {
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
