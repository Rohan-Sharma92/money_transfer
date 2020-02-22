package com.revolut.moneytransfer.fxrates;

import java.math.BigDecimal;

import com.revolut.moneytransfer.model.Currency;

public interface IFxRateStore {

	BigDecimal getConversionRate(Currency sourceCurrency,Currency targetCurrency);
	
	void addConversionRate(Currency sourceCurrency,Currency targetCurrency,BigDecimal value);
}
