package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;

public class ShareContainer {
	private Share share;
	private int amount;
	private BigDecimal payedPrice;
	
	public ShareContainer(Share share, int amount) {
		this.share = share;
		this.amount = amount;
		this.payedPrice = share.getValue().multiply(new BigDecimal(amount));
	}
	
	public Share getShare() {
		return share;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void buy(int amount) {
		setAmount(amount);
	}
	
	public int sell(int amount) {
		if (this.amount > amount) {
			int actualAmount = this.amount;
			setAmount(-actualAmount);
			return actualAmount;
		}
		setAmount(-amount);
		return amount;
	}
	
	private void setAmount(int amount) {
		this.amount += amount;
		this.payedPrice = this.payedPrice.add(share.getValue().multiply(new BigDecimal(amount)));
	}
	
	public BigDecimal getPayedPrice() {
		return payedPrice;
	}
	
	public boolean isEmpty() {
		return amount == 0;
	}
	
}
