package qu.master.ir.isi.ds;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalcUtils {
	
	public static double getTermFrequency(List<String> terms, String term) {
		double result = 0;
		
		List<String> similarTerms = terms.stream().filter(s -> s.equals(term)).collect(Collectors.toList());
		
		if (similarTerms != null) {
			result = 1 + Math.log10(similarTerms.size());
		}
		
		return result;
	}
	
	public static double normalizeDocument(Document document) {
		double sum = 0;
		double result = 0;
		
		for(Term term : document.getTerms()) {
			sum += Math.pow(term.getFreqMult(), 2);
		}
		
		result = Math.sqrt(sum);
		return result;
	}
	
	public static double getSimilarity(Document document1, Document document2) {
		double result = 0;
		
		for(Term term1 : document1.getTerms()) {
			Optional<Term> opt = document2.getTerms().stream().filter(t -> t.getText().equals(term1.getText())).findFirst();
			
			if (opt.isPresent()) {
				result += term1.getFreqMult() * opt.get().getFreqMult();
			}
		}
		
		return result;
	}
}
