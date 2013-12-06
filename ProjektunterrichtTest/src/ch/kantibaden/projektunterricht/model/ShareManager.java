package ch.kantibaden.projektunterricht.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class ShareManager {

	private ArrayList<Share> shares = new ArrayList<Share>();
	private final String ALL_SHARES = "%40%5EDJI,%40%5EGDAXI,GOOG";

	public ShareManager() throws IOException {
		downloadAllShares();// STARTING SHARES

		// DOWNLOAD FOR EACH SHARE THE CHART
		for (Share currentShare : shares) {
			currentShare.downloadImage();
		}
	}

	public ArrayList<Share> getShares() {
		return shares;
	}

	private void downloadAllShares() throws IOException {
		// Read an http resource in to a stream
		String downloadLink = "http://download.finance.yahoo.com/d/quotes.csv?s=" + ALL_SHARES + "&f=l1s0n0&e=.csv";
		URL url = new URL(downloadLink);
		URLConnection connection = url.openConnection();
		connection.connect();
		BufferedReader bufferedCSV = new BufferedReader(new InputStreamReader(url.openStream()));

		String line;
		while ((line = bufferedCSV.readLine()) != null) {
			String[] splittedLine = line.split(",");
			//TODO: overwrite existing shares!
			try {
				shares.add(new Share(splittedLine[2], splittedLine[1], new BigDecimal(splittedLine[0])));
			} catch (Exception e) {
				System.out.println("failed adding " + splittedLine[2] + " : " + e);
			}
		}

		for (Share shareObj : shares) {
			System.out.println(shareObj.getName() + "  " + shareObj.getSymbol() + "  " + shareObj.getValue());
		}
	}

}
