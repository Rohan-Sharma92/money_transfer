package com.revolut.moneytransfer.validation.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.revolut.moneytransfer.validation.IValidationRule;
import com.revolut.moneytransfer.validation.IValidationRuleEngine;

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
