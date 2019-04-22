package qu.master.ir.isi.models;

public class SearchSource {
	
	private String url;
	private double score;
	private boolean enabled;
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public double getScore() {
		return this.score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}