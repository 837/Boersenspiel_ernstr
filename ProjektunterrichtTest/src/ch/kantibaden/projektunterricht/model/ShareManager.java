package ch.kantibaden.projektunterricht.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ShareManager {
	private ArrayList<Share> allSharesList = new ArrayList<Share>();
	private final String ALL_SHARES = "%40%5EDJI,%40%5EGDAXI,GOOG";
	private final int VALUE = 10000000;

	public static void main(String[] args) throws IOException {
		ShareManager manager = new ShareManager();
		manager.downloadAllShares();

		manager.downloadSingleShare("GOOG");
	}

	private void downloadAllShares() throws IOException {
		// Read an http resource in to a stream
		String tAddress = "http://download.finance.yahoo.com/d/quotes.csv?s="
				+ ALL_SHARES + "&f=l1s0n0&e=.csv";
		URL tDocument = new URL(tAddress);
		URLConnection tConnection = tDocument.openConnection();
		tConnection.connect();
		BufferedReader tBufferedReader = new BufferedReader(
				new InputStreamReader(tDocument.openStream()));

		String line;
		while ((line = tBufferedReader.readLine()) != null) {
			String[] splittedLine = line.split(",");

			try {

				allSharesList.add(new Share(splittedLine[2], splittedLine[1],
						new BigDecimal(splittedLine[0])));

				// TESTING
				// String Wert = splittedLine[0];
				// String symbol = splittedLine[1];
				// String name = splittedLine[2];
				// System.out.println(Wert + "  " + name + "  " + symbol);
			} catch (Exception e) {
				System.out.println("Yaaay (k)ein Fehler!!");
			}
		}
		System.out.println("Fertig, Fehler ignorieren.");

		for (Share shareObj : allSharesList) {
			System.out.println(shareObj.getShareName() + "  "
					+ shareObj.getShareSymbol() + "  "
					+ shareObj.getCurrentValue());
		}
	}

	private void downloadSingleShare(String shareSymbol) throws IOException {
		// Read an http resource in to a stream
		String tAddress = "http://download.finance.yahoo.com/d/quotes.csv?s="
				+ shareSymbol + "&f=l1s0n0&e=.csv";
		URL tDocument = new URL(tAddress);
		URLConnection tConnection = tDocument.openConnection();
		tConnection.connect();
		BufferedReader tBufferedReader = new BufferedReader(
				new InputStreamReader(tDocument.openStream()));

		String line;
		while ((line = tBufferedReader.readLine()) != null) {
			String[] splittedLine = line.split(",");

			try {
				for (Share shareObj : allSharesList) {
					if (shareObj.getShareSymbol().contains(shareSymbol)) {
						// Wert zu beginn
						System.out.println(shareObj.getShareName() + "  "
								+ shareObj.getShareSymbol() + "  "
								+ shareObj.getCurrentValue());

						// shareObj.setCurrentValue(new
						// BigDecimal(splittedLine[0]));
						shareObj.setCurrentValue(new BigDecimal(VALUE));

						// wert nach änderung
						System.out.println(shareObj.getShareName() + "  "
								+ shareObj.getShareSymbol() + "  "
								+ shareObj.getCurrentValue());

					}
				}
			} catch (Exception e) {
				System.out.println("Yaaay (k)ein Fehler!!");
			}
		}
		System.out.println("Fertig, Fehler ignorieren.");

	}
}
