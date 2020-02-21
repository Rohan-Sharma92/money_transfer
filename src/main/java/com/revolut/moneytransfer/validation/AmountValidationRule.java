package com.revolut.moneytransfer.validation;

import java.math.BigDecimal;
import java.util.Map;

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
		builder.append(AMOUNT).append("is invalid.");
		return builder.toString();
	}

}
