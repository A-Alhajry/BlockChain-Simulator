package stocksanomaly.webapp.controllers;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

import stocksanomaly.webapp.models.*;

@WebServlet(name = "BasicStocksApi", urlPatterns = {"/api/service"})
public class BasicStocksRestApi extends HttpServlet {
	
	@Inject
	private StocksRepositoryInterface repo;
	
	Map<String, Function<HttpServletRequest, String>> actionsHandlers;
	
	@Override
	public void init(){
		try {
			actionsHandlers = new HashMap<>();
			actionsHandlers.put("latest_data", (req) -> getLatestStocks(req));
			actionsHandlers.put("comp_list", (req) -> getCompaniesList(req));
			actionsHandlers.put("markets_list", (req) -> getMarketsList(req));
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String action = request.getParameter("action");
		
		try {
			
			String json = actionsHandlers.get(action).apply(request);
			
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String action = request.getParameter("action");
		
		try {
			
			String json = "";
			
			if (action.equals("comp_list")) {
				json = getCompaniesList(request);
			}
			
			else if (action.equals("markets_list")) {
				json = getMarketsList(request);
			}
			
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	private String getLatestStocks(HttpServletRequest request) {
		
		try {
			String companyName = request.getParameter("compname");
			String marketName = request.getParameter("market");
			List<StockAnomalyDataBean> stocks = repo.getLatestStocks(marketName, companyName, 5);
			Gson gson = new Gson();
			return gson.toJson(stocks);
		}
		
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private String getCompaniesList(HttpServletRequest request) {
		
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
			
			LocalDate startDate = LocalDate.parse(request.getParameter("startdate"), formatter);
			LocalDate endDate = LocalDate.parse(request.getParameter("enddate"), formatter);
			String companyName = request.getParameter("compname");
			
			List<StockAnomalyDataBean> stocks = repo.getStocks(null, startDate, endDate, companyName);
			
			double aSum = 0;
			double fSum = 0;
			
			for(StockAnomalyDataBean b : stocks) {
				aSum += b.getActualValue();
				fSum += b.getForecastedValue();
			}
			
			double[] arr = {aSum, fSum};
			Gson gson = new Gson();
			return gson.toJson(stocks);
		}
		
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	private String getMarketsList(HttpServletRequest request) {
		try {
			List<StocksMarket> markets = repo.getStocksMarkets();
			Gson gson = new Gson();
			return gson.toJson(markets);
		}
		
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
