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
import stocksanomaly.webapp.models.IndexStats;
import stocksanomaly.webapp.models.MarketHistory;
import stocksanomaly.webapp.models.StockAnomalyDataBean;
import stocksanomaly.webapp.models.StocksMarket;
import stocksanomaly.webapp.models.StocksPeriodicData;
import stocksanomaly.webapp.models.StocksRepository;
import stocksanomaly.webapp.models.StocksRepositoryInterface;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"} )
public class HomeController extends HttpServlet {
	
	@Inject
	private StocksRepositoryInterface repo;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			List<StocksMarket> markets = repo.getStocksMarkets();
			List<MarketHistory> history = repo.getMarketsHistory();
			IndexStats indexStats = repo.getIndexStats();
			List<StocksPeriodicData> yearlyData = repo.getYearlyData(2017);
			Gson gson = new Gson();
			req.setAttribute("Markets", gson.toJson(markets));
			req.setAttribute("History", gson.toJson(history));
			req.setAttribute("IndexStats", gson.toJson(indexStats));
			req.setAttribute("YearlyData", gson.toJson(yearlyData));
			req.getRequestDispatcher("WEB-INF/views/home.jsp").forward(req, res);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<StocksMarket> getMarkets() throws Exception {
		return repo.getStocksMarkets();
	}
}