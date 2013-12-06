package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;

public class ShareContainer {
	private Share share;
	private int amount;
	private BigDecimal payedPrice;
	private String symbol;
	
	public ShareContainer(Share share, int amount, String symbol){
		this.share = share;
		this.amount = amount;
		this.payedPrice = null;
	}
}
