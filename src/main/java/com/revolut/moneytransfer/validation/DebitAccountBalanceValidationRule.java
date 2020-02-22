package com.revolut.moneytransfer.validation;

import java.math.BigDecimal;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.revolut.moneytransfer.model.Currency;
import com.revolut.moneytransfer.model.IBalance;
import com.revolut.moneytransfer.repo.IGenericRepository;
import com.revolut.moneytransfer.util.MoneyTransferUtil;

public class DebitAccountBalanceValidationRule implements IValidationRule {

	private final IGenericRepository<IBalance, String> balanceCache;
	private String errorMessage = StringUtils.EMPTY;

	@Inject
	public DebitAccountBalanceValidationRule(final IGenericRepository<IBalance, String> balanceCache) {
		this.balanceCache = balanceCache;
	}

	@Override
	public boolean isValid(Map<String, Object> requestMap) {
		if (requestMap.containsKey("debitAccount") && requestMap.containsKey("amount")
				&& requestMap.containsKey("currency")) {
			String balanceKey = MoneyTransferUtil.generateBalanceKey(requestMap.get("debitAccount").toString(),
					(Currency.valueOf((String) requestMap.get("currency"))));
			IBalance existingBalance = balanceCache.findById(balanceKey);
			if (existingBalance == null) {
				errorMessage = "Invalid currency for debit account";
				return false;
			}
			BigDecimal expectedBalance = existingBalance.getAmount()
					.subtract(BigDecimal.valueOf((Double) requestMap.get("amount")));
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
