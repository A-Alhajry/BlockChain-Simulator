package qu.master.ir.isi.ds;

import java.util.*;

public class Collection {

	
	public static double getIDocFreq(String term) {
		
		Map<String, Double> map = new HashMap<>();
		map.put("world", 1.0);
		map.put("cup", 2.0);
		map.put("qatar", 2.0);
		map.put("follows", 3.0);
		map.put("2022", 3.0);
		
		return map.get(term);
	}
}
