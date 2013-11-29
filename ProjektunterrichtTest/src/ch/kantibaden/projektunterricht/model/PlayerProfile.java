package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;
import java.util.ArrayList;


public class PlayerProfile {

	private String name;
	private String email;
	private String password;
	private BigDecimal beginningBalance;
	private BigDecimal balance;
	private ArrayList<Share> ownedShares;

	public PlayerProfile(String name, String email, String password, int beginningBalance) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.beginningBalance = new BigDecimal(beginningBalance);

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

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the ownedShares
	 */
	public ArrayList<Share> getOwnedShares() {
		return ownedShares;
	}

	/**
	 * @param ownedShares
	 *            the ownedShares to set
	 */
	public void setOwnedShares(ArrayList<Share> ownedShares) {
		this.ownedShares = ownedShares;
	}

	/**
	 * @return the beginningBalance
	 */
	public BigDecimal getBeginningBalance() {
		return beginningBalance;
	}

}
