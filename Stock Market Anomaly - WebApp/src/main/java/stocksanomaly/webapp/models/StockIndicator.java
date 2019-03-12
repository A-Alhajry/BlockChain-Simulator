package stocksanomaly.webapp.models;

public enum StockIndicator {
	
	UP(1),
	DOWN(2),
	NO_CHANGE(3);
	
	private int value;
	
	private StockIndicator(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
