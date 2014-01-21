package ch.kantibaden.projektunterricht.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

public class ShareManager {

	private ArrayList<Share> shares = new ArrayList<Share>();
	private final String ALL_SHARES = "%40%5EDJI,%40%5EGDAXI,GOOG";
	private Random rnd = new Random();
	private String csvFile = "Shares\1.csv";

	public ShareManager() {
	}

	public void loadPlayerShares(ArrayList<ShareContainer> playerShares) throws IOException {
		for (ShareContainer currentShareContainer : playerShares) {
			shares.add(currentShareContainer.getShare());
		}
		downloadAll();
	}

	private void getRndCsv() {

		csvFile = "Shares\\" + rnd.nextInt(4) + ".csv";
	}

	public void downloadAll() throws IOException {
		/*
		 * // download all shares String downloadLink =
		 * "http://download.finance.yahoo.com/d/quotes.csv?s=" + ALL_SHARES +
		 * "&f=l1s0n0&e=.csv"; System.out.println(downloadLink); URL url = new
		 * URL(downloadLink); URLConnection connection = url.openConnection();
		 * connection.setConnectTimeout(2000);// timeout after 2sec
		 * connection.setReadTimeout(2000); connection.connect();
		 */

		getRndCsv();
		BufferedReader bufferedCSV = new BufferedReader(new FileReader(csvFile));

		String line;
		while ((line = bufferedCSV.readLine()) != null) {
			line = line.replaceAll("\"", "");
			System.out.println(line);
			String[] splittedLine = line.split(",");
			boolean foundShare = false;
			if (splittedLine.length > 1) {
				for (Share currentShare : shares) {
					if (currentShare.getSymbol().equals(splittedLine[1])) {
						currentShare.setValue(new BigDecimal(splittedLine[0]));
						foundShare = true;
					}
				}

				if (!foundShare) {
					if (splittedLine.length == 4) {// name contains ','
						shares.add(new Share(splittedLine[2] + splittedLine[3], splittedLine[1], new BigDecimal(splittedLine[0])));
					} else if (splittedLine.length == 3) {// normal case
						shares.add(new Share(splittedLine[2], splittedLine[1], new BigDecimal(splittedLine[0])));
					} else {
						System.out.println("Weird Yahoo input: \"" + line + "\"");
					}
				}
			}
		}

		for (Share currentShare : shares) {
			System.out.println(currentShare.getName() + "  " + currentShare.getSymbol() + "  " + currentShare.getValue());
			currentShare.setChart(null);
		}
	}

	public ArrayList<Share> getShares() {
		return shares;
	}

}
