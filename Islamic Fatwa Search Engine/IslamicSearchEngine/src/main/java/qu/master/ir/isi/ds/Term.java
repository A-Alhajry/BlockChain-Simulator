package qu.master.ir.isi.ds;

public class Term {
	
	private String text;
	private double termFreq;
	private double iDocFreq;
	
	public Term(String text, double termFreq, double iDocFreq) {
		this.text = text;
		this.termFreq = termFreq;
		this.iDocFreq = iDocFreq;
	}
	
	public String getText() {
		return this.text;
	}
	
	public double getTermFreq() {
		return this.termFreq;
	}
	
	public double getIDocFreq() {
		return this.iDocFreq;
	}
	
	public double getFreqMult() {
		return this.termFreq * this.iDocFreq;
	}
	
	
}
