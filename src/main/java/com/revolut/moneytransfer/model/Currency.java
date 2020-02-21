package com.revolut.moneytransfer.model;

public enum Currency {

	EUR(1, "Euro"), USD(2, "US Dollars"), GBP(3, "British Pound"), INR(4, "Indian Rupee");

	private final Integer intValue;
	private final String displayString;

	private Currency(final Integer intValue, final String displayString) {
		this.intValue = intValue;
		this.displayString = displayString;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public String getDisplayString() {
		return displayString;
	}
}
