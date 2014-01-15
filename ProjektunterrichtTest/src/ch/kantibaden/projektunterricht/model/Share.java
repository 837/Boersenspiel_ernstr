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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Share {

	private StringProperty name = new SimpleStringProperty();
	private StringProperty symbol = new SimpleStringProperty();
	private StringProperty value = new SimpleStringProperty();
	private StringProperty difference = new SimpleStringProperty();
	private Image chart;
	private BigDecimal oldValue;

	public Share(String name, String symbol, BigDecimal value) {
		this.name.set(name);
		this.symbol.set(symbol);
		this.value.set(value.toString());
	}

	public BigDecimal getOldValue() {
		return oldValue;
	}

	public void setOldValue(BigDecimal oldValue) {
		this.oldValue = oldValue;
	}

	public String getDifference() {
		return difference.get();
	}

	public Image getChart() {
		if (chart == null) {
			setChart(new Image(getImagePath()));
		}
		return chart;
	}

	public void setChart(Image chart) {
		this.chart = chart;
	}

	public String getName() {
		return name.get();
	}

	public String getSymbol() {
		return symbol.get();
	}

	public BigDecimal getValue() {
		return new BigDecimal(value.get());
	}

	public void setValue(BigDecimal newValue) {
		this.value.set(newValue.toString());
	}

	public String getImagePath() {
		return "http://chart.finance.yahoo.com/z?s=" + symbol.get().replaceAll("\"", "") + "&t=2m&q=l&l=on&z=l&p=m20,m50,m200";
	}

	public void downloadImage() {
		try {
			URL url = new URL("http://chart.finance.yahoo.com/z?s=" + symbol.get().replaceAll("\"", "") + "&t=2m&q=l&l=on&z=l&p=m20,m50,m200");

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
			FileOutputStream fos = new FileOutputStream("Charts\\" + symbol.get().replace('\"', ' ') + "_Chart.png");
			fos.write(response);
			fos.close();

			System.out.println("Chart für " + symbol + " heruntergeladen.");
		} catch (IOException e) {
			System.out.println("Ein Fehler!! in downloadImage " + symbol + "   " + e);
		}
	}

}
