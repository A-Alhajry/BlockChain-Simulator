package qu.master.ir.isi.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.squareup.okhttp.*;
import com.squareup.okhttp.Request.Builder;

public class HttpClient {
	
	public static String doGet(String url, Map<String, String> headers) throws Exception {
		
		OkHttpClient client = new OkHttpClient();
		Builder builder = new Request.Builder();
		builder.url(url);
		
		if (headers != null) {
			Set<Entry<String, String>> set = headers.entrySet();
			for(Entry<String, String> entry : set) {
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		Request request = builder.build();
		
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
