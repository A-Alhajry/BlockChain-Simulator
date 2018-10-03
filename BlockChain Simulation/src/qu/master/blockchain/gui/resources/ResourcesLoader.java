package qu.master.blockchain.gui.resources;

import java.net.URL;

public class ResourcesLoader {
	
	private static URL loadingIcon;
	
	public ResourcesLoader() {
		loadingIcon = getClass().getResource("loading.gif");
	}
	
	public URL getLoadingIcon() {
		return loadingIcon;
	}
}
