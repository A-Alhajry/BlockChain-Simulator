package stocksanomaly.webapp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MarketSummary {

	private LocalDate date;
	private String dateString;
	private int totalAnomaly;
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getDateString() {
		return this.dateString;
	}
	
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	
	public int getTotalAnomaly() {
		return this.totalAnomaly;
	}
	
	public void setTotalAnomaly(int totalAnomaly) {
		this.totalAnomaly = totalAnomaly;
	}
}
