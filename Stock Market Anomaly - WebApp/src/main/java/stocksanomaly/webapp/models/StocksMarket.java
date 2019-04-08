package stocksanomaly.webapp.models;

public class StocksMarket {
	
	private int id;
	private String name;
	private String logo;
	private String indexName;
	private double actualIndex;
	private double forecastedIndex;
	private int totalStocks;
	private int totalAnomals;
	private boolean isOpen;
	private StocksMarketHealth health;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogo() {
		return this.logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getIndexName() {
		return this.indexName;
	}
	
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	
	public double getActualIndex() {
		return this.actualIndex;
	}
	
	public void setActualIndex(double actualIndex) {
		this.actualIndex = actualIndex;
	}
	
	public double getForecastedIndex() {
		return this.forecastedIndex;
	}
	
	public void setForecastedIndex(double forecastedIndex) {
		this.forecastedIndex = forecastedIndex;
	}
	
	public int getTotalStocks() {
		return this.totalStocks;
	}
	
	public void setTotalStocks(int totalStocks) {
		this.totalStocks = totalStocks;
	}
	
	public int getTotalAnomals() {
		return this.totalAnomals;
	}
	
	public void setTotalAnomals(int totalAnomals) {
		this.totalAnomals = totalAnomals;
	}
	
	public boolean getIsOpen() {
		return this.isOpen;
	}
	
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public StocksMarketHealth getHealth() {
		return this.health;
	}
	
	public void setHealth(StocksMarketHealth health) {
		this.health = health;
	}
	
	
}
