package qu.master.ir.isi.core;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import qu.master.ir.isi.models.*;

public abstract class AbstractSearchResultsSorter extends AbstractPostSearchStep implements SearchResultsSorter {
	
	private int resultsMax = 10;
	
	protected List<SearchResult> doProcess(List<SearchResult> result) {
		Collections.sort(result, this);
		return result.stream().distinct().limit(resultsMax).collect(Collectors.toList());
	}
	
	public int getResultsMax() {
		return resultsMax;
	}
	
	public void setResultsMax(int resultsMax) {
		this.resultsMax = resultsMax;
	}
	
	
}
