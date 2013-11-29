package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;

public class Share {
	private BigDecimal value;
	// Full share name
	private String name;
	// Short share name
	private String symbol;

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
