package ch.kantibaden.projektunterricht.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Share {
	private BigDecimal value;
	private String name;
	private String symbol;// the name by which the share is referenced to when downloading from Yahoo
	
	public Share(String name, String symbol, BigDecimal value) {
		this.name = name;
		this.symbol = symbol;
		this.value = value;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void update() throws IOException {
		System.out.println("updating " + symbol);
		String downloadLink = "http://download.finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=l1&e=.csv";
		URL url = new URL(downloadLink);
		URLConnection connection = url.openConnection();
		connection.connect();
		BufferedReader bufferedCSV = new BufferedReader(new InputStreamReader(url.openStream()));
		
		String line = bufferedCSV.readLine();
		value = new BigDecimal(line);
	}
	
	public void downloadImage() {
		try {
			URL url = new URL("http://chart.finance.yahoo.com/z?s="
					// + symbol.replace('\"', ' ')
					// .replaceAll(" ", "")
					+ symbol.replaceAll("\"", "") 
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
			FileOutputStream fos = new FileOutputStream("Charts\\" + symbol.replace('\"', ' ') + "_Chart.png");
			fos.write(response);
			fos.close();
			
			System.out.println("Chart f�r " + symbol + " heruntergeladen.");
		} catch (IOException e) {
			System.out.println("Ein Fehler!! in downloadImage " + symbol + "   " + e);
		}
	}
	
}
