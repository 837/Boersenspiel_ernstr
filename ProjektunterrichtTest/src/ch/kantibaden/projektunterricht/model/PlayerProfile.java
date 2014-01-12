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
	
	public BigDecimal getTotalShareValue() {
		BigDecimal totalValue = new BigDecimal(0);
		
		for (ShareContainer currentShare : ownedShares) {
			totalValue = totalValue.add(currentShare.getShare().getValue().multiply(new BigDecimal(currentShare.getAmount())));
		}
		
		return totalValue;
	}
	
	/**
	 * @return the ownedShares
	 */
	public ArrayList<ShareContainer> getOwnedShares() {
		return ownedShares;
	}
	
	/**
	 * @return the ownedSharesByShare
	 */
	public int getOwnedSharesByShare(Share share) {
		for (ShareContainer currentShare : ownedShares) {
			if (currentShare.getShare().equals(share)) {
				return currentShare.getAmount();
			}
		}
		return 0;
	}
	
	/**
	 * @return the beginningBalance
	 */
	public BigDecimal getBeginningBalance() {
		return beginningBalance;
	}
	
	public boolean buy(Share shareToBuy, int amount) {
		if (shareToBuy.getValue().multiply(new BigDecimal(amount)).compareTo(getBalance()) == 1) {
			System.out.println("Sorry not enough money! You would need: " + shareToBuy.getValue().multiply(new BigDecimal(amount)));
			return false;
		}
		
		System.out.println(getBalance());
		this.balance = getBalance().subtract(shareToBuy.getValue().multiply(new BigDecimal(amount))); 
		System.out.println(getBalance());
		transactions.add(new Transaction(shareToBuy, amount));
		System.out.println("Just bought: " +amount+" "+ shareToBuy.getName() + "  " + shareToBuy.getValue().multiply(new BigDecimal(amount)));
		
		for (ShareContainer currentShare : ownedShares) {
			if (currentShare.getShare() == shareToBuy) {
				currentShare.buy(amount);
				return true;
			}
		}
		ownedShares.add(new ShareContainer(shareToBuy, amount));
		return true;
	}
	
	public boolean sell(Share shareToSell, int amount) {
		boolean couldSell = false;
		int actuallAmount = 0;
		
		for (ShareContainer currentShareContainer : getOwnedShares()) {
			
			if (currentShareContainer.getShare() == shareToSell) {
				actuallAmount = currentShareContainer.sell(amount);
				couldSell = true;
				if (currentShareContainer.isEmpty()) {
					ownedShares.remove(currentShareContainer);
				}
				break;
			}
		}
		
		if (couldSell) {
			System.out.println(getBalance());
			this.balance = getBalance().add(shareToSell.getValue().multiply(new BigDecimal(actuallAmount)));
			System.out.println(getBalance());
			transactions.add(new Transaction(shareToSell, -actuallAmount));
			System.out.println("Just sold: " +actuallAmount+" "+ shareToSell.getName() + "  " + shareToSell.getValue().multiply(new BigDecimal(amount)));
			
		}
		return couldSell;
	}
	
}
