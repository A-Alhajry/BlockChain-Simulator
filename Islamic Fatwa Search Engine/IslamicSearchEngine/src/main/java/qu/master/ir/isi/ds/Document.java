package qu.master.ir.isi.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Document {
	
	private List<Term> terms;
	
	public Document(String docText) {
		this.terms = new ArrayList<>();
		List<String> words = getWords(docText);
		for(String termText: words) {
			double iDocFreq = Collection.getIDocFreq(termText);
			double termFreq = CalcUtils.getTermFrequency(words, termText);
			Term term = new Term(termText, termFreq, iDocFreq);
			this.terms.add(term);
			
		}
	}
	
	public List<Term> getTerms() {
		return this.terms;
	}
	
	private List<String> getWords(String text) {
		String[] words = text.split(" ");
		return Arrays.asList(words).stream().map(t -> t.toLowerCase()).collect(Collectors.toList());
	}
}
