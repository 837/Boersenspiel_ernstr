package ch.kantibaden.projektunterricht.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.Date;

import ch.kantibaden.projektunterricht.dao.UserDao;

public class Transaction {
	private ShareContainer shares;
	private Date date;

	public Transaction(Share share, int amount) {
		this.shares = new ShareContainer(share, amount);// negatice amount means
														// sell
		date = new Date();
		System.out.println("TRANSACTION: " + date + "  " + share.getName() + "  " + share.getValue().multiply(new BigDecimal(amount)));

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(UserDao.getUser().getName()+"_Transactions.txt",
				true)))) {
			out.println("TRANSACTION: " + date);
			if (share.getValue().multiply(new BigDecimal(amount)).intValue() < 0) {
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

		} catch (IOException e) {
			System.out.println("Transactionlog error in outputstream");
		}

	}
}
