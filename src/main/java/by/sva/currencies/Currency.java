package by.sva.currencies;

public class Currency {
	
	private String name;
	private double buy;
	private double sell;
	
	public Currency(String name, double buy, double sell) {
		super();
		this.name = name;
		this.buy = buy;
		this.sell = sell;
	}

	public String getName() {
		return name;
	}

	public double getBuy() {
		return buy;
	}

	public double getSell() {
		return sell;
	}

}
