package qu.master.ir.isi.core;

public abstract class AbstractSearchEngine implements SearchEngineInterface {
	
	private String engineName;
	private double engineScore;
	private boolean enabled;
	private int resultsMax;
	
	public AbstractSearchEngine(String engineName) {
		this.engineName = engineName;
	}
	
	public String getEngineName() {
		return this.engineName;
	}
	
	public double getEngineScore() {
		return this.engineScore;
	}
	
	public void setEngineScore(double engineScore) {
		this.engineScore = engineScore;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public int getResultsMax() {
		return this.resultsMax;
	}
	
	public void setResultsMax(int resultsMax) {
		this.resultsMax = resultsMax;
	}
	
}