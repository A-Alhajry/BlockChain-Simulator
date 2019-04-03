package stocksanomaly.webapp.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import stocksanomaly.webapp.models.CompanySummary;
import stocksanomaly.webapp.models.StockAnomalyDataBean;
import stocksanomaly.webapp.models.StocksRepository;
import stocksanomaly.webapp.models.StocksRepositoryInterface;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"} )
public class HomeController extends HttpServlet {
	
	@Inject
	private StocksRepositoryInterface repo;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			req.getRequestDispatcher("index.jsp").forward(req, res);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}