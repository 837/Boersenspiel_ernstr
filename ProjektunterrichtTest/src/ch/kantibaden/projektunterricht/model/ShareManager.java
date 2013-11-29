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
		for (Share shareObj : shares) {
			downloadShareImage(shareObj);
		}
	}

	private void downloadAllShares() throws IOException {
		// Read an http resource in to a stream
		String downloadLink = "http://download.finance.yahoo.com/d/quotes.csv?s="
				+ ALL_SHARES + "&f=l1s0n0&e=.csv";
		URL url = new URL(downloadLink);
		URLConnection connection = url.openConnection();
		connection.connect();
		BufferedReader bufferedCSV = new BufferedReader(new InputStreamReader(
				url.openStream()));

		String line;
		while ((line = bufferedCSV.readLine()) != null) {
			String[] splittedLine = line.split(",");

			try {
				shares.add(new Share(splittedLine[2], splittedLine[1], new BigDecimal(splittedLine[0])));
			} catch (Exception e) {
				System.out.println("failed adding " + splittedLine[2] + " : " + e);
			}
		}

		for (Share shareObj : shares) {
			System.out.println(shareObj.getShareName() + "  "
					+ shareObj.getShareSymbol() + "  "
					+ shareObj.getCurrentValue());
		}
	}

	private void refreshSingleShare(Share share) throws IOException {
		// Read an http resource in to a stream
		String downloadLink = "http://download.finance.yahoo.com/d/quotes.csv?s="
				+ share.getShareSymbol() + "&f=l1&e=.csv";
		URL url = new URL(downloadLink);
		URLConnection connection = url.openConnection();
		connection.connect();
		BufferedReader bufferedCSV = new BufferedReader(new InputStreamReader(url.openStream()));

		String value = bufferedCSV.readLine();
		share.setValue(new BigDecimal(value));

	}

	private void downloadShareImage(Share share) throws IOException {

		try {
			URL url = new URL("http://chart.finance.yahoo.com/z?s="
					+ share.getShareSymbol().replace('\"', ' ')
							.replaceAll(" ", "")
					+ "&t=2m&q=l&l=on&z=l&p=m20,m50,m200");

			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();

			// save image
			FileOutputStream fos = new FileOutputStream("Charts\\"
					+ share.getShareSymbol().replace('\"', ' ') + "_Chart.png");
			fos.write(response);
			fos.close();

			System.out.println("Chart für " + share.getShareSymbol()
					+ " heruntergeladen.");
		} catch (IOException e) {
			System.out.println("Ein Fehler!! in downloadSingleShareImage "
					+ share.getShareSymbol() + "   " + e);
		}
	}
}
