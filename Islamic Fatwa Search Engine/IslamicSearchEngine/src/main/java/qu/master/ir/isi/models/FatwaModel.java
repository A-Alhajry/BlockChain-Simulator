package qu.master.ir.isi.models;

import java.time.LocalDate;

public class FatwaModel {
	
	private int rank;
	private String title;
	private String question;
	private String answer;
	private String mufti;
	private LocalDate date;
	private String link;
	private String snippet;
	
	public int getRank() {
		return this.rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
	
	public String getMufti() {
		return this.mufti;
	}
	
	public void setMufti(String mufti) {
		this.mufti = mufti;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getSnippet() {
		return this.snippet;
	}
	
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
}
