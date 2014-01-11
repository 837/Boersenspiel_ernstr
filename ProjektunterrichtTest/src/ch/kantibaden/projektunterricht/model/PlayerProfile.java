package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayerProfile {
	
	private String name;
	private String email;
	private String password;
	private BigDecimal beginningBalance;
	private BigDecimal balance;
	private ArrayList<ShareContainer> ownedShares;
	private ArrayList<Transaction> transactions;
	
	public PlayerProfile(String name, String email, String password, int beginningBalance) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.beginningBalance = new BigDecimal(beginningBalance);
		this.balance = new BigDecimal(this.beginningBalance.toString());
		this.ownedShares = new ArrayList<>();
		this.transactions = new ArrayList<>();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}
	
	public BigDecimal getTotalShareValue(){
		BigDecimal totalValue = new BigDecimal(0);
		
		for (ShareContainer currentShare : ownedShares){
			totalValue.add(currentShare.getShare().getValue());
		}
		
		return totalValue;
	}
	
	/**
	 * @return the ownedShares
	 */
	public ArrayList<Share> getOwnedShares() {
		ArrayList<Share> shares = new ArrayList<>();
		for (ShareContainer currentShare : ownedShares){
			shares.add(currentShare.getShare());
		}
		return shares;
	}
	
	/**
	 * @return the beginningBalance
	 */
	public BigDecimal getBeginningBalance() {
		return beginningBalance;
	}
	
	public boolean buy(Share shareToBuy, int amount) {
		if (shareToBuy.getValue().multiply(new BigDecimal(amount)).compareTo(balance) == -1) {// don't have enough money
			return false;
		}
		balance.subtract(shareToBuy.getValue().multiply(new BigDecimal(amount)));
		transactions.add(new Transaction(shareToBuy, amount));
		for (ShareContainer currentShare : ownedShares) {
			if (currentShare.getShare() == shareToBuy) {// this works because it actually is the same object
				currentShare.buy(amount);
				return true;
			}
		}
		ownedShares.add(new ShareContainer(shareToBuy, amount));
		return true;
	}
	
	public boolean sell(Share shareToSell, int amount) {
		boolean couldSell = false;
		int actuallySoldAmount = 0;
		
		
		for (Iterator<ShareContainer> it = ownedShares.iterator(); it.hasNext();) {
			ShareContainer currentShare = it.next();
			if (currentShare.getShare() == shareToSell) {// this works because they reference the same object
				actuallySoldAmount = currentShare.sell(amount);//could sell that many shares?
				couldSell = true;
				if (currentShare.isEmpty()) {
					ownedShares.remove(shareToSell);
				}
				break;
			}
		}
		
		if (couldSell) {
			balance.add(shareToSell.getValue().multiply(new BigDecimal(actuallySoldAmount)));
			transactions.add(new Transaction(shareToSell, -actuallySoldAmount));
		}
		return couldSell;
	}
	
	
}
