package qu.master.ir.isi.ds;

public class CosineSimilarity {
	
	public static double calculate(String string1, String string2) {
		
		Document document1 = new Document(string1);
		Document document2 = new Document(string2);
		
		double documentNormalized1 = CalcUtils.normalizeDocument(document1);
		double documentNormalized2 = CalcUtils.normalizeDocument(document2);
		
		double documentsSimilarity = CalcUtils.getSimilarity(document1, document2);
		
		return documentsSimilarity / (documentNormalized1 * documentNormalized2);
	}
	
}