package qu.master.ir.isi.core;

import java.util.List;

import qu.master.ir.isi.models.SearchResult;

public abstract class AbstractPostSearchStep implements PostSearchStep {
	
	private PostSearchStep nextStep;
	
	public PostSearchStep getNextStep() {
		return this.nextStep;
	}
	
	public void setNextStep(PostSearchStep nextStep) {
		this.nextStep = nextStep;
	}
	
	public List<SearchResult> process(List<SearchResult> result) {
		
		List<SearchResult> newResult = this.doProcess(result);
		
		if (this.nextStep == null) {
			return newResult;
		}
		
		else {
			return this.nextStep.process(newResult);
		}
	}
	
	protected abstract List<SearchResult> doProcess(List<SearchResult> result);
	
	
}
