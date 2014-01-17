package ch.kantibaden.projektunterricht.model;

import java.math.BigDecimal;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Share {

	private StringProperty name = new SimpleStringProperty();
	private StringProperty symbol = new SimpleStringProperty();
	private StringProperty value = new SimpleStringProperty();
	private StringProperty difference = new SimpleStringProperty();
	private Image chart;
	
	public Share(String name, String symbol, BigDecimal value) {
		this.name.set(name);
		this.symbol.set(symbol);
		this.value.set(value.toString());
		this.difference.set("0");
	}

	public String getDifference() {
		return difference.get();
	}

	public Image getChart() {
		if (chart == null) {
			chart = new Image("http://chart.finance.yahoo.com/z?s=" + symbol.get().replaceAll("\"", "") + "&t=1d&q=l&l=on&z=l");
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
		difference.set(newValue.subtract(new BigDecimal(value.get())).toString());
			this.value.set(newValue.toString());
	}


}
