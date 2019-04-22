package qu.master.ir.isi.core;

import qu.master.ir.isi.models.SearchQuery;

public class BasicPreSearchStep extends AbstractPreSearchStep {
	
	protected SearchQuery doProcess(SearchQuery query) {
		return query;
	}
}
