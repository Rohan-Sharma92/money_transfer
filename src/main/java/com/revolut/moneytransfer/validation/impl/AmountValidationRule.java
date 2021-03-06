package com.revolut.moneytransfer.validation.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.revolut.moneytransfer.util.Constants;
import com.revolut.moneytransfer.validation.IValidationRule;

public class AmountValidationRule implements IValidationRule {

	public static final String AMOUNT = "amount";

	@Override
	public boolean isValid(final Map<String, Object> requestMap) {
		boolean isValid = true;
		if (requestMap.containsKey(AMOUNT)) {
			BigDecimal bigDecimal = BigDecimal.valueOf((Double)requestMap.get(AMOUNT));
			return bigDecimal.compareTo(BigDecimal.ZERO) >= 0;
		}
		return isValid;
	}

	@Override
	public String getErrorMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append(AMOUNT).append(Constants.IS_INVALID);
		return builder.toString();
	}

}
