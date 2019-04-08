package stocksanomaly.webapp.models;

import java.util.List;

public class MarketHistory {
	
	private int id;
	private String name;
	private List<MarketSummary> history;
	
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
	
	public List<MarketSummary> getHistory() {
		return this.history;
	}
	
	public void setHistory(List<MarketSummary> history) {
		this.history = history;
	}
}
