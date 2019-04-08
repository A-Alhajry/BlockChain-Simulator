package stocksanomaly.webapp.models;

public class StocksPeriodicData {
	
	private String label;
	private int regularsCount;
	private int anomalsCount;
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getRegularsCount() {
		return this.regularsCount;
	}
	
	public void setRegularsCount(int regularsCount) {
		this.regularsCount = regularsCount;
	}
	
	public int getAnomalsCount() {
		return this.anomalsCount;
	}
	
	public void setAnomalsCount(int anomalsCount) {
		this.anomalsCount = anomalsCount;
	}
}
