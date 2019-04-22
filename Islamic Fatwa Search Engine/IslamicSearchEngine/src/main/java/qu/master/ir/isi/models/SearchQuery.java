package qu.master.ir.isi.models;

import java.util.List;

public class SearchQuery implements Cloneable {
	
	private String text;
	private String url;
	private int count;
	private List<SearchSource> sources;
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public List<SearchSource> getSources() {
		return this.sources;
	}
	
	public void setSources(List<SearchSource> sources) {
		this.sources = sources;
	}
	
	public SearchQuery clone() {
		SearchQuery clone = new SearchQuery();
		clone.setText(this.getText());
		clone.setCount(this.getCount());
		clone.setUrl(this.getUrl());
		
		return clone;
	}
}
