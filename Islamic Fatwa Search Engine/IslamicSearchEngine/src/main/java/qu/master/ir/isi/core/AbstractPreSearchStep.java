package qu.master.ir.isi.core;

import qu.master.ir.isi.models.SearchQuery;

public abstract class AbstractPreSearchStep implements PreSearchStep {
	
	private PreSearchStep nextStep;
	
	public PreSearchStep getNextStep() {
		return this.nextStep;
	}
	
	public void setNextStep(PreSearchStep nextStep) {
		this.nextStep = nextStep;
	}
	
	public SearchQuery process(SearchQuery query) {
		SearchQuery result = this.doProcess(query);
		
		if (this.nextStep != null) {
			return nextStep.process(result);
		}
		
		else {
			return result;
		}
	}
	
	protected abstract SearchQuery doProcess(SearchQuery query);
}
