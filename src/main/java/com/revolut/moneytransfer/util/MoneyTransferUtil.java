package com.revolut.moneytransfer.util;

import org.apache.commons.lang3.StringUtils;

import com.revolut.moneytransfer.model.Currency;

public class MoneyTransferUtil {

	public static String generateBalanceKey(String accountId, Currency currency) {
		return StringUtils.join(new String[] { accountId, currency.getDisplayString() }, '_');
	}
}
