package qu.master.ir.isi.core;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.azure.cognitiveservices.search.websearch.*;
import com.microsoft.azure.cognitiveservices.search.websearch.models.*;

import qu.master.ir.isi.models.BingSearchResult;
import qu.master.ir.isi.models.FatwaModel;
import qu.master.ir.isi.models.SearchQuery;
import qu.master.ir.isi.models.SearchResult;
import qu.master.ir.isi.models.SearchSource;
import qu.master.ir.isi.services.*;

public class BingSearchEngine extends AbstractSearchEngine {
	
	private static final String customConfig  = "customconfig=439c1e9a-7b74-48df-a63e-b82cce8a8d7b";
	private String searchMode = "custom";
	
	public BingSearchEngine() {
		super("Bing");
	}
	
	public List<SearchResult> search(SearchQuery query) throws Exception{
		
		List<SearchResult> result;
		if (this.searchMode.toLowerCase().equals("custom")) {
			result = doCustomSearch(query);
		}
		
		else {
			result = doGenericSearch(query);
		}

		return result;
	}
	
	private List<SearchResult> doGenericSearch(SearchQuery query) throws Exception{
		List<SearchResult> result = new ArrayList<SearchResult>();
		BingWebSearchAPI client = BingWebSearchManager.authenticate(getKeys().get(0));
		List<List<WebPage>> pagesList = new ArrayList<>();
		for(SearchSource source : query.getSources()) {
			SearchResponse response = getSearchResponse(client, query, source);
			pagesList.add(getWebPages(response));
		}
		
		result.addAll(getSearchResults(reorderPages(pagesList, query.getCount() / query.getSources().size())));
		
		return result;
	}
	
	private List<SearchResult> doCustomSearch(SearchQuery query) throws Exception {
		List<SearchResult> result = new ArrayList<>();
		List<String> keys = getKeys();
		String appKey = keys.get(1);
		String custId = keys.get(2);
		String url = "https://api.cognitive.microsoft.com/bingcustomsearch/v7.0/search?";
		
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append("q=" + query.getText());
		sb.append("&customconfig=" + custId);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Ocp-Apim-Subscription-Key", appKey);
		
		String json = HttpClient.doGet(sb.toString(), headers);
		result = getSearchResultsFromJson(json);
		
		return result;
	}
	
	public FatwaModel readFatwa(String link) {
		
		FatwaModel fm = new FatwaModel();
		return fm;
	}
	
	private List<String> getKeys() throws Exception {
		List<String> keys = new ArrayList<>();
		InputStream path = this.getClass().getResourceAsStream("bingkey");
		BufferedReader br = new BufferedReader(new InputStreamReader(path));
		String line;
		while ((line = br.readLine()) != null) {
			keys.add(line);
		}
		return keys;
	}
	
	private SearchResponse getSearchResponse(BingWebSearchAPI client, SearchQuery query, SearchSource source) {
		SearchResponse response = client.bingWebs().search().withQuery(getSiteSearchFilter(query, source)).withCount(query.getCount() / query.getSources().size()).execute();
		return response;
	}
	
	private String getSiteSearchFilter(SearchQuery query, SearchSource source) {
		return "site:" + source.getUrl() + " " + query.getText();
	}
	
	private List<WebPage> getWebPages(SearchResponse response) {
		
		List<WebPage> pages = new ArrayList<WebPage>();
		if (response != null && response.webPages() != null && response.webPages().value() != null && response.webPages().value().size() != 0) {
			
			for(WebPage page : response.webPages().value()) {
				pages.add(page);
			}
		}
		
		return pages;
	}
	
	private List<SearchResult> getSearchResultsFromJson(String json) {
		List<SearchResult> results = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonObject root = parser.parse(json).getAsJsonObject();
		JsonObject webPagesProp = root.getAsJsonObject("webPages");
		JsonArray webPagesArray = webPagesProp.getAsJsonArray("value");
		LocalDateTime timestamp = LocalDateTime.now();
		
		for(int i = 0; i < webPagesArray.size(); i++) {
			JsonObject pageItem = webPagesArray.get(i).getAsJsonObject();
			SearchResult  result = new BingSearchResult();
			result.setTitle(pageItem.get("name").getAsString());
			result.setSnippet(pageItem.get("snippet").getAsString());
			result.setUrl(pageItem.get("url").getAsString());
			result.setRank(i + 1);
			result.setSourceEngine(this);
			result.setTimestamp(timestamp);
			results.add(result);
		}
		
		return results;
	}
	
	private List<SearchResult> getSearchResults(List<WebPage> pages) {
		
		List<SearchResult> results = new ArrayList<>();
		LocalDateTime timestamp = LocalDateTime.now();
		for(int i = 0; i < pages.size(); i++) {
			WebPage page = pages.get(i);
			SearchResult current =  new BingSearchResult();
			current.setRank(i + 1);
			current.setTitle(page.name());
			current.setUrl(page.url());
			current.setTimestamp(timestamp);
			current.setSnippet(page.snippet());
			current.setSourceEngine(this);
			results.add(current);
		}
		return results;
	}
	
	private List<WebPage> reorderPages(List<List<WebPage>> pages, int count) {
		List<WebPage> result = new ArrayList<WebPage>();
		
		for(int i = 0; i < count; i++) {
			for(List<WebPage> page : pages) {
				result.add(page.get(i));
			}
		}
		
		return result;
	}
	
	public String getSearchMode() {
		return this.searchMode;
	}
	
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	

}
