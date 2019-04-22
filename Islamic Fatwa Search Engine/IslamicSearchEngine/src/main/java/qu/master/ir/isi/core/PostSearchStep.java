package qu.master.ir.isi.core;

import java.util.List;

import qu.master.ir.isi.models.SearchResult;

public interface PostSearchStep extends SearchStep {
	
	public List<SearchResult> process(List<SearchResult> result);
	public PostSearchStep getNextStep();
	public void setNextStep(PostSearchStep step);
}
