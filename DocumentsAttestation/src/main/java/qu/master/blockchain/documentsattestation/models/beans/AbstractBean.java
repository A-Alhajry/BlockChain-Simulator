package qu.master.blockchain.documentsattestation.models.beans;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractBean implements Serializable {
	
	public String toJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
	public AbstractBean fromJson(String json) {
		Gson gson = new Gson();
		Class<?> currentClass = this.getClass();
		
		try {
			return (AbstractBean) gson.fromJson(json, currentClass);
		}
		
		catch (Exception e) {
			throw new IllegalArgumentException("Invalid Json");
		}
		
	}
}
