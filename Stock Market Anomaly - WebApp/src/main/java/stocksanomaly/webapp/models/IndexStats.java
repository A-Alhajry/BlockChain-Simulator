package stocksanomaly.webapp.models;

import java.util.List;

public class IndexStats {
	
	private String source;
	private int stocks;
	private int regulars;
	private int anomals;
	private int regularsPerc;
	private int anomalsPerc;
	private int stables;
	private int ok;
	private int unstables;
	private int unknowns;
	private List<IndexStats> subStats;
	
	public String getSource() {
		return this.getSource();
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public int getStocks() {
		return this.stocks;
	}
	
	public void setStocks(int stocks) {
		this.stocks = stocks;
	}
	
	public int getRegulars() {
		return this.regulars;
	}
	
	public void setRegulars(int regulars) {
		this.regulars = regulars;
	}
	
	public int getAnomals() {
		return this.anomals;
	}
	
	public void setAnomals(int anomals) {
		this.anomals = anomals;
	}
	
	public int getRegularsPerc() {
		return this.regularsPerc;
	}
	public void setRegularsPerc(int regularsPerc) {
		this.regularsPerc = regularsPerc;
	}
	
	public int getAnomalsPerc() {
		return this.anomalsPerc;
	}
	
	public void setAnomalsPerc(int anomalsPerc) {
		this.anomalsPerc = anomalsPerc;
	}
	
	public int getStables() {
		return this.stables;
	}
	
	public void setStables(int stables) {
		this.stables = stables;
	}
	
	public int getOk() {
		return this.ok;
	}
	
	public void setOk(int ok) {
		this.ok = ok;
	}
	
	public int getUnstables() {
		return this.unstables;
	}
	
	public void setUnstables(int unstables) {
		this.unstables = unstables;
	}
	
	public int getUnknowns() {
		return this.unknowns;
	}
	
	public void setUnknowns(int unknowns) {
		this.unknowns = unknowns;
	}
	public List<IndexStats> getSubStats() {
		return this.subStats;
	}
	
	public void setSubStats(List<IndexStats> subStats) {
		this.subStats = subStats;
	}
	
}
