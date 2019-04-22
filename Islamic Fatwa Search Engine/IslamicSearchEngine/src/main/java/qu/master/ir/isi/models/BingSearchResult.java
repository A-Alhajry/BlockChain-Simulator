package qu.master.ir.isi.models;

public class BingSearchResult extends SearchResult {
	
	private String source;
	
	public BingSearchResult() {
		this.source = "Bing";
	}
	
	public FatwaModel getFatwa() {
		FatwaModel fm = new FatwaModel();
		fm.setLink(super.getUrl());
		return fm;
	}
	
	public String getSource() {
		return this.source;
	}
}
