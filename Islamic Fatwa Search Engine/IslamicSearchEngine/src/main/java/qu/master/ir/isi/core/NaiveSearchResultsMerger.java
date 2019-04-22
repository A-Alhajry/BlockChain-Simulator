package qu.master.ir.isi.core;

import java.util.Collections;
import java.util.List;

import qu.master.ir.isi.models.SearchResult;

public class NaiveSearchResultsMerger extends AbstractSearchResultsSorter{
	
	
	public int compare(SearchResult r1, SearchResult r2) {
		if (r1.getRank() == r2.getRank()) {
			return (int) (r2.getSourceEngine().getEngineScore() - r1.getSourceEngine().getEngineScore());
		}
		
		else {
			return r1.getRank() - r2.getRank();
		}
	}
}
