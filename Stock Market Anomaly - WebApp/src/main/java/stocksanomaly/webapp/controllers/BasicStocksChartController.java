package stocksanomaly.webapp.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import stocksanomaly.webapp.models.StockAnomalyDataBean;
import stocksanomaly.webapp.models.StocksRepositoryInterface;

@WebServlet(name = "StocksChartController1", urlPatterns = {"/basic"})

public class BasicStocksChartController extends HttpServlet {
	
	@Inject
	private StocksRepositoryInterface repo;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			repo.setDataSource(getServletContext().getResourceAsStream("/StocksDump.txt"));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String demoPage = "/WEB-INF/views/demo" + req.getParameter("demo") + ".jsp";
			LocalDate startDate = LocalDate.parse(req.getParameter("startdate"), formatter);
			LocalDate endDate = LocalDate.parse(req.getParameter("enddate"), formatter);
			String company = req.getParameter("company");
			
			List<StockAnomalyDataBean> stocks = repo.getStocks(startDate, endDate, company);
			Gson gson = new Gson();
			String stocksAsJson = gson.toJson(stocks);
			req.setAttribute("Stocks", stocksAsJson);
			req.getRequestDispatcher(demoPage).forward(req, res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
