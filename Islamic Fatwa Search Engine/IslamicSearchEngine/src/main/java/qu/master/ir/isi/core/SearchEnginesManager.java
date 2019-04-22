package qu.master.ir.isi.core;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import qu.master.ir.isi.models.FatwaModel;
import qu.master.ir.isi.models.SearchQuery;
import qu.master.ir.isi.models.SearchResult;
import qu.master.ir.isi.models.SearchSource;

public class SearchEnginesManager {
	
	private static SearchEnginesManager instance = new SearchEnginesManager();
	private List<SearchEngineInterface> searchEngines;
	private List<SearchSource> searchSources;
	private List<PreSearchStep> preSearchSteps;
	private List<PostSearchStep> postSearchSteps;
	
	public SearchEnginesManager() {
		
	}
	
	public static SearchEnginesManager getInstance() {
		
		return instance;
	}
	
	public List<SearchEngineInterface> getSearchEngines() {
		return this.searchEngines;
	}
	
	public void setSearchEngines(List<SearchEngineInterface> searchEngines) {
		this.searchEngines = searchEngines;
	}
	
	public List<SearchSource> getSearchSources() {
		return this.searchSources;
	}
	
	public void setSearchSources(List<SearchSource> searchSources) {
		this.searchSources = searchSources;
	}
	
	public void setPreSearchSteps(List<PreSearchStep> preSearchSteps) {
		this.preSearchSteps = preSearchSteps;
	}
	
	public void setPostSearchSteps(List<PostSearchStep> postSearchSteps) {
		this.postSearchSteps = postSearchSteps;
	}
	
	public List<SearchResult> search(SearchQuery query) throws Exception{
		
		List<SearchResult> result = new ArrayList<>();
		query = processQuery(query);
		query.setSources(new ArrayList<>());
		for(SearchSource source : searchSources) {
			if (source.isEnabled()) {
				query.getSources().add(source);
			}
		}
		for(SearchEngineInterface se : getSearchEngines()) {
			if (se.isEnabled()) {
				result.addAll(se.search(query));
			}
			
			
		}
		return processResult(result);
	}
	
	public List<FatwaModel> searchFatwa(SearchQuery query) throws Exception {
		List<SearchResult> sr = this.search(query);
		List<FatwaModel> fm = sr.stream().map(r -> r.getFatwa()).collect(Collectors.toList());
		return fm;
	}
	
	public FatwaModel readFatwa(String engine, String link) {
		
		for(SearchEngineInterface se : this.searchEngines) {
			if (se.getEngineName().equals(engine)) {
				return se.readFatwa(link);
			}
		}
		
		return null;
	}
	
	private SearchQuery processQuery(SearchQuery query) {
		PreSearchStep step = preSearchSteps.get(0);
		return step.process(query);
	}
	
	private List<SearchResult> processResult(List<SearchResult> result) {
		PostSearchStep step = postSearchSteps.get(0);
		return step.process(result);
	}
//	public List<SearchEngineInterface> getSearchEngines() {
//		List<SearchEngineInterface> engines = new ArrayList<>();
//		engines.add(new BingSearchEngine());
//		return engines;
//	}
}
