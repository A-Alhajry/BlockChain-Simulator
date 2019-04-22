package qu.master.ir.isi.core;

import java.util.List;

import qu.master.ir.isi.models.FatwaModel;
import qu.master.ir.isi.models.SearchQuery;
import qu.master.ir.isi.models.SearchResult;

public interface SearchEngineInterface {
	
	List<SearchResult> search(SearchQuery query) throws Exception;
	FatwaModel readFatwa(String link);
	String getEngineName();
	double getEngineScore();
	boolean isEnabled();
	//boolean setEnabled(boolean enabled);
}
