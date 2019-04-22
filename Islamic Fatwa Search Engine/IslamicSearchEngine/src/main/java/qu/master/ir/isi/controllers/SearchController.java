package qu.master.ir.isi.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import qu.master.ir.isi.models.*;

@Controller
public class SearchController {
	
	@RequestMapping(value = "/home", method = RequestMethod.GET) 
	public ModelAndView home() throws Exception {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}	
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(String text) throws Exception {
		SearchQuery sq = new SearchQuery();
		sq.setText(text);
		sq.setCount(10);
		List<SearchResult> sr = BeansFactory.getSearchEnginesManager().search(sq);
		ModelAndView mv = new ModelAndView("result");
		mv.addObject("Result", sr);
		mv.addObject("SearchText", text);
		return mv;
	}
}
