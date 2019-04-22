package qu.master.ir.isi.core;

import qu.master.ir.isi.models.SearchQuery;

public interface PreSearchStep extends SearchStep{
	
	public SearchQuery process(SearchQuery query);
	public void setNextStep(PreSearchStep step);
	public PreSearchStep getNextStep();
}
