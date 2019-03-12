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
import stocksanomaly.webapp.models.StocksRepositoryInterface;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"} )
public class HomeController extends HttpServlet {
	
	@Inject
	private StocksRepositoryInterface repo;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			repo.setDataSource(getServletContext().getResourceAsStream("/StocksDump.txt"));
//			List<StockAnomalyDataBean> stocks = repo.getStocks(LocalDate.MIN, LocalDate.MAX, "QP");
//			req.setAttribute("Stocks", stocks);
			List<CompanySummary> companiesData = repo.getCompaniesSummaries();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate defaultStartDate = LocalDate.of(2018, 6, 1);
			LocalDate defaultEndDate = LocalDate.of(2018, 6, 30);
			Gson gson = new Gson();
			req.setAttribute("CompaniesData", companiesData);
			req.setAttribute("startDate", gson.toJson(defaultStartDate));
			req.setAttribute("endDate", gson.toJson(defaultEndDate));
			req.getRequestDispatcher("index.jsp").forward(req, res);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}