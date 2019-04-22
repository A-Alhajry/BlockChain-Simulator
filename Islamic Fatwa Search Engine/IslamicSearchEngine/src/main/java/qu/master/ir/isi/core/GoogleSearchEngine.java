package qu.master.ir.isi.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.javanet.*;
import com.google.api.client.json.jackson2.*;
import com.google.api.services.customsearch.*;
import com.google.api.services.customsearch.model.*;

import qu.master.ir.isi.models.*;

public class GoogleSearchEngine extends AbstractSearchEngine {
	
	private static String keysFile = "googlekey";
	
	private static String apiKey;
	private static String cx;
	private static String appName = "Islamic Search Engine";
	
	public GoogleSearchEngine() {
		super("Google");
	}
	
	public List<SearchResult> search(SearchQuery query) throws Exception {
		List<SearchResult> sr = new ArrayList<>();
		setKeys();
		Customsearch cs = getCustomsearch();
		Search search = searchGoogle(cs, query);
		sr = getSearchResults(search);
		return sr;
	}
	
	public FatwaModel readFatwa(String link) {
		FatwaModel fm = new FatwaModel();
		
		return fm;
	}
	
	public String getEngineName() {
		return "Google";
	}
	
	private Customsearch getCustomsearch() {
		return 	new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
	}
	
	private Search searchGoogle(Customsearch cs, SearchQuery query) throws Exception{
		String queryText = query.getText() + " site:" + query.getUrl();
		Customsearch.Cse.List list = cs.cse().list(query.getText());
		list.setKey(apiKey);
		list.setCx(cx);
		list.setNum((long) query.getCount());
		return list.execute();
	}
	
	private List<SearchResult> getSearchResults(Search search) {
		List<SearchResult> sr = new ArrayList<>();
		int totalCount = search.getItems().size();
		LocalDateTime timestamp = LocalDateTime.now();
		
		for(int i = 0; i < totalCount; i++) {
			Result result = search.getItems().get(i);
			SearchResult searchResult = new GoogleSearchResult();
			searchResult.setRank(i + 1);
			searchResult.setUrl(result.getLink());
			searchResult.setTitle(result.getTitle());
			searchResult.setTimestamp(timestamp);
			searchResult.setSnippet(result.getSnippet());
			searchResult.setSourceEngine(this);
			
			sr.add(searchResult);
		}
		
		return sr;
	}
	private void setKeys() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(keysFile)));
		apiKey = reader.readLine();
		cx = reader.readLine();
	}
}
