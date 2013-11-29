package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;

public class Share {
	private BigDecimal value;
	private String name;
	private String symbol;//the name by which the share is referenced to when downloading from Yahoo

	public Share(String name, String symbol, BigDecimal value) {
		this.name = name;
		this.symbol = symbol;
		this.value = value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

}
