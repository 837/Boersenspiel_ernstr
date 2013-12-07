package ch.kantibaden.projektunterricht.model;

import java.util.Date;

public class Transaction {
	private ShareContainer shares;
	private Date date;
	
	public Transaction(Share share, int amount) {
		this.shares = new ShareContainer(share, amount);//negatice amount means sell
		date = new Date();
	}
}
