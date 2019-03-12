package stocksanomaly.webapp.models;

import java.time.LocalDate;

public class CompanySummary {
	
	private String fullName;
	private String shortName;
	private String iconUrl;
	private StockIndicator indicator;
	private double actualValue;
	private double forecastedValue;
	private double differenceValue;
	private double differencePercentage;
	private LocalDate date;
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getIconUrl() {
		return this.iconUrl;
	}
	
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public StockIndicator getIndicator() {
		return this.indicator;
	}
	
	public void setIndicator(StockIndicator indicator) {
		this.indicator = indicator;
	}
	
	public double getActualValue() {
		return this.actualValue;
	}
	
	public void setActualValue(double actualValue) {
		this.actualValue = actualValue;
	}
	
	public double getForecastedValue() {
		return this.forecastedValue;
	}
	
	public void setForecastedValue(double forecastedValue) {
		this.forecastedValue = forecastedValue;
	}
	
	public double getDifferenceValue() {
		return this.differenceValue;
	}
	
	public void setDifferenceValue(double differenceValue) {
		this.differenceValue = differenceValue;
	}
	
	public double getDifferencePercentage() {
		return this.differencePercentage;
	}
	
	public void setDifferencePercentage(double differencePercentage) {
		this.differencePercentage = differencePercentage;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}


