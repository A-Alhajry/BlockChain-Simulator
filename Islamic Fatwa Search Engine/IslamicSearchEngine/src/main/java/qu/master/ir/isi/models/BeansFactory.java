package qu.master.ir.isi.models;

import org.springframework.context.*;
import org.springframework.context.support.*;

import qu.master.ir.isi.core.SearchEnginesManager;

public class BeansFactory {
	
	public static <T> T getBean(String name) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/beans.xml");
		return (T) ctx.getBean(name);
	}
	
	public static SearchEnginesManager getSearchEnginesManager() {
		return getBean("seMgr");
	}
}
