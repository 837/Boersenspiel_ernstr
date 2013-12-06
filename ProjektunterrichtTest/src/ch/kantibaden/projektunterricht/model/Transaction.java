package ch.kantibaden.projektunterricht.model;

import java.util.Date;


public class Transaction {
	private ShareContainer shares;
	private Date date;
	
	public Transaction(ShareContainer shares){
		this.shares = new ShareContainer(shares);
		date = new Date();
	}
}
