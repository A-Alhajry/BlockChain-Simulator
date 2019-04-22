package qu.master.ir.isi.models;

import java.time.LocalDateTime;

import qu.master.ir.isi.core.SearchEngineInterface;

public abstract class SearchResult {
	
	private String title;
	private String url;
	private String snippet;
	private int rank;
	private double score;
	private LocalDateTime timestamp;
	private String question;
	private String answer;
	private SearchEngineInterface sourceEngine;
	private SearchQuery query;
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSnippet() {
		return this.snippet;
	}
	
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public double getScore() {
		return this.score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public abstract FatwaModel getFatwa();
	
	public SearchEngineInterface getSourceEngine() {
		return this.sourceEngine;
	}
	
	public void setSourceEngine(SearchEngineInterface sourceEngine) {
		this.sourceEngine = sourceEngine;
	}
	
	public SearchQuery getQuery() {
		return this.query;
	}
	
	public void setQuery(SearchQuery query) {
		this.query = query;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof SearchResult) {
			SearchResult r = (SearchResult) o;
			
			if (r.getTitle() == null || this.title == null) {
				return false;
			}
			
			else {
				return this.title.equals(r.getTitle());
			}
		}
		
		else {
			return false;
		}
		
		
	}
	
	@Override
	public int hashCode() {
		return this.title.hashCode();
	}
	
}
