package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PlayerProfile {
	
	private String name;
	private String password;
	private BigDecimal beginningBalance;
	private BigDecimal balance;
	private ArrayList<ShareContainer> ownedShares;
	private ArrayList<Transaction> transactions;
	
	public PlayerProfile(String name, String password, BigDecimal bigDecimal) {
		this.name = name;
		this.password = password;
		this.beginningBalance = bigDecimal;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
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
	public int getOwnedAmountOfShare(Share share) {
		for (ShareContainer currentShare : ownedShares) {
			if (currentShare.getShare()==share) {
				return currentShare.getAmount();
			}
		}
		return 0;
	}
	
	public BigDecimal getTotalValueOfShare(Share share) {
		for (ShareContainer currentShare : ownedShares) {
			if (currentShare.getShare()==share) {
				return currentShare.getShare().getValue().multiply(new BigDecimal(currentShare.getAmount()));
			}
		}
		return new BigDecimal(0);
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
		System.out.println("Just bought: " + amount + " " + shareToBuy.getName() + "  "
				+ shareToBuy.getValue().multiply(new BigDecimal(amount)));
		
		for (ShareContainer currentShare : ownedShares) {
			if (currentShare.getShare() == shareToBuy) {
				currentShare.buy(amount);
				return true;
			}
		}
		ownedShares.add(new ShareContainer(shareToBuy, amount));
		return true;
	}
	
	public int sell(Share shareToSell, int amount) {
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
			System.out.println("Just sold: " + actuallAmount + " " + shareToSell.getName() + "  "
					+ shareToSell.getValue().multiply(new BigDecimal(amount)));
			
		}
		return actuallAmount;
	}
	
}
