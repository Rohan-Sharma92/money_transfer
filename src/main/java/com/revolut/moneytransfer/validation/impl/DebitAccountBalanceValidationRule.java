package com.revolut.moneytransfer.validation.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.repo.IBalanceRepository;
import com.revolut.moneytransfer.util.MoneyTransferUtil;
import com.revolut.moneytransfer.validation.IValidationRule;

public class DebitAccountBalanceValidationRule implements IValidationRule {

	private static final String CURRENCY = "currency";
	private static final String AMOUNT = "amount";
	private static final String DEBIT_ACCOUNT = "debitAccount";
	private final IBalanceRepository balanceCache;
	private String errorMessage = StringUtils.EMPTY;

	@Inject
	public DebitAccountBalanceValidationRule(final IBalanceRepository balanceCache) {
		this.balanceCache = balanceCache;
	}

	@Override
	public boolean isValid(Map<String, Object> requestMap) {
		if (requestMap.containsKey(DEBIT_ACCOUNT) && requestMap.containsKey(AMOUNT)
				&& requestMap.containsKey(CURRENCY)) {
			String balanceKey = MoneyTransferUtil.generateBalanceKey(requestMap.get(DEBIT_ACCOUNT).toString(),
					(Currency.valueOf((String) requestMap.get(CURRENCY))));
			IBalance existingBalance = balanceCache.findById(balanceKey);
			if (existingBalance == null) {
				errorMessage = "Invalid currency for debit account";
				return false;
			}
			BigDecimal expectedBalance = existingBalance.getAmount()
					.subtract(BigDecimal.valueOf((Double) requestMap.get(AMOUNT)));
			if (expectedBalance.compareTo(BigDecimal.ZERO) < 0) {
				errorMessage = "Debit account balance is insufficient";
				return false;
			}
		}
		return true;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

}
