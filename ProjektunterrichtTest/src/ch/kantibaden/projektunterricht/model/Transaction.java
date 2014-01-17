package ch.kantibaden.projektunterricht.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;

import ch.kantibaden.projektunterricht.dao.UserDao;

public class Transaction {
	private ShareContainer shares;
	private Date date;
	private BigDecimal balance;

	public Transaction(Share share, int amount, BigDecimal playerCapital) {
		this.shares = new ShareContainer(share, amount);// negative amount means sell
		this.date = new Date();
		this.balance = playerCapital;
		System.out.println("TRANSACTION: " + date + "  " + share.getName() + "  " + share.getValue().multiply(new BigDecimal(amount)));

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(UserDao.getUser().getName()+"_Transactions.txt",
				true)))) {
			out.println("TRANSACTION: " + date);
			if (amount < 0) {
				out.println("TYP: VERKAUF");
				amount *= -1;
			} else {
				out.println("TYP: KAUF");
			}
			out.println("AKTIE: " + share.getName());
			out.println("MENGE: " + amount);
			out.println("EINZELNER WERT: " + share.getValue());
			out.println("TOTALER WERT: " + share.getValue().multiply(new BigDecimal(amount)));
			out.println("******************************************************************");
			out.println("Neues Gesamtkapital: " + playerCapital);
			out.println("******************************************************************");

		} catch (IOException e) {
			System.out.println("Transactionlog error in outputstream");
		}

	}
}
