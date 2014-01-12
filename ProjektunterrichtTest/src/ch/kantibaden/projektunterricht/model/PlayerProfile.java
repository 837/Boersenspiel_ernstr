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
		if (shareToBuy.getValue().multiply(new BigDecimal(amount)).compareTo(getBalance()) == 1) {// don't
																									// have
																									// enough
																									// money
			System.out.println("Sorry not enough money! You would need: " + shareToBuy.getValue().multiply(new BigDecimal(amount)));
			return false;
		}

		System.out.println(getBalance());
		// getBalance().subtract(shareToBuy.getValue().multiply(new
		// BigDecimal(amount))); //DAS GAHT NED,
		this.balance = getBalance().subtract(shareToBuy.getValue().multiply(new BigDecimal(amount))); // HINGEGE
																										// DAS
																										// GAHT
																										// 0.o
		System.out.println(getBalance());

		transactions.add(new Transaction(shareToBuy, amount));
		System.out.println("Just bought: " + shareToBuy.getName() + "  " + shareToBuy.getValue().multiply(new BigDecimal(amount)));

		for (ShareContainer currentShare : ownedShares) {
			if (currentShare.getShare() == shareToBuy) {// this works because it
														// actually is the same
														// object
				currentShare.buy(amount);
				return true;
			}
		}
		ownedShares.add(new ShareContainer(shareToBuy, amount));
		return true;
	}

	/* HIER STIMMT ETWAS NOCH NICHT, ES GIBT IMMER TRUE ZURÜCK */
	public boolean sell(Share shareToSell, int amount) {
		boolean couldSell = false;
		int actuallySoldAmount = 0;

		for (ShareContainer currentShareContainer : getOwnedShares()) {

			if (currentShareContainer.getShare() == shareToSell) {// this works because
															// they reference
															// the same object
				actuallySoldAmount = currentShareContainer.sell(amount);// could sell
																// that many
																// shares?
				couldSell = true;
				if (currentShareContainer.isEmpty()) {
					ownedShares.remove(currentShareContainer);
				}
				break;
			}
		}

		if (couldSell) {
			System.out.println(getBalance());
			// balance.add(shareToSell.getValue().multiply(new
			// BigDecimal(actuallySoldAmount))); THIS DOES NOT WORKS

			this.balance = getBalance().add(shareToSell.getValue().multiply(new BigDecimal(actuallySoldAmount))); // THIS
																													// DOES
			System.out.println(getBalance());

			transactions.add(new Transaction(shareToSell, -actuallySoldAmount));

			System.out.println("Just sold: " + shareToSell.getName() + "  " + shareToSell.getValue().multiply(new BigDecimal(amount)));

		}
		return couldSell;
	}

}
