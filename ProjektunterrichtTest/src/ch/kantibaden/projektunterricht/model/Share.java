package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;

public class Share {
	private BigDecimal currentValue;
	// Full share name
	private String shareName;
	// Short share name
	private String shareSymbol;

	public Share(String shareName, String shareSymbol, BigDecimal currentValue) {
		this.shareName = shareName;
		this.shareSymbol = shareSymbol;
		this.currentValue = currentValue;
	}

	public BigDecimal getCurrentValue() {
		return currentValue;
	}

	public String getShareName() {
		return shareName;
	}

	public String getShareSymbol() {
		return shareSymbol;
	}

}
