package stocksanomaly.webapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class StockAnomalyDataBean {
	
	private long stockId;
	private String name;
	private String company;
	private LocalDate date;
	private double forecastedValue;
	private double actualValue;
	private double difference;
	private double percentage;
	private boolean isAnomaly;
	
	public StockAnomalyDataBean() {
		
	}
	
	public StockAnomalyDataBean(long id, String name, String company, LocalDate date, double forecasted, double actual, boolean isAnomaly, StockAnomalyDataBean previous) {
		this.stockId = id;
		this.name = name;
		this.company = company;
		this.date = date;
		this.forecastedValue = roundStockData(forecasted);
		this.actualValue = roundStockData(actual);
		this.difference = roundStockData(actual - forecasted);
		this.isAnomaly = isAnomaly;
		
		if (previous != null) {
			double previousValue = previous.getActualValue();
			double differenceBetween = actual - previousValue;
			
			if (previousValue == actual) {
				this.percentage = 0.00;
			}
			
			else if (previousValue < actual) {
				this.percentage = (differenceBetween / previousValue) * 100;
			}
			
			else {
				this.percentage = (-differenceBetween / previousValue) * 100;
			}
		}
		
		this.percentage = roundStockData(this.percentage);
		
	}
	
	public long getStockId() {
		return this.stockId;
	}
	
	public void setStockId(long stockId) {
		this.stockId = stockId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public double getForecastedValue() {
		return this.forecastedValue;
	}
	
	public void setForecastedValue(double forecastedValue) {
		this.forecastedValue = forecastedValue;
	}
	
	public double getActualValue() {
		return this.actualValue;
	}
	
	public void setActualValue(double actualValue) {
		this.actualValue = actualValue;
	}
	
	public boolean getIsAnomaly() {
		return this.isAnomaly;
	}
	
	public void setIsAnomaly(boolean isAnomaly) {
		this.isAnomaly = isAnomaly;
	}
	
	public double getDifference() {
		return this.difference;
	}
	
	public void setDifference(double difference) {
		this.difference = difference;
	}
	
	public double getPercentage() {
		return this.percentage;
	}
	
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	public String getShortDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
		return this.date.format(formatter);
	}
	
	private double roundStockData(double data) {
		return Math.round(data * 100) / 100.0;
	}
}
