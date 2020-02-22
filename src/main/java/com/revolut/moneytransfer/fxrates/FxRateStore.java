package com.revolut.moneytransfer.fxrates;

import java.math.BigDecimal;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.revolut.moneytransfer.model.Currency;

public class FxRateStore implements IFxRateStore {

	Table<Currency, Currency, BigDecimal> conversionRates = HashBasedTable.create();

	@Override
	public BigDecimal getConversionRate(Currency sourceCurrency, Currency targetCurrency) {
		return conversionRates.get(sourceCurrency, targetCurrency);
	}

	@Override
	public void addConversionRate(Currency sourceCurrency, Currency targetCurrency, BigDecimal value) {
		conversionRates.put(sourceCurrency, targetCurrency, value);
	}

}
