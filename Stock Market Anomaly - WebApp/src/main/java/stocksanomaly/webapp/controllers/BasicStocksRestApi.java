package stocksanomaly.webapp.controllers;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

import stocksanomaly.webapp.models.*;

@WebServlet(name = "BasicStocksApi", urlPatterns = {"/api/basic"})
public class BasicStocksRestApi extends HttpServlet {
	
	@Inject
	StocksRepositoryInterface repo;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		repo.setDataSource(getServletContext().getResourceAsStream("/StocksDump.txt"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		
		LocalDate startDate = LocalDate.parse(request.getParameter("startdate"), formatter);
		LocalDate endDate = LocalDate.parse(request.getParameter("enddate"), formatter);
		String companyName = request.getParameter("compname");
		
		try {
			List<StockAnomalyDataBean> stocks = repo.getStocks(startDate, endDate, companyName);
			Gson gson = new Gson();
			PrintWriter pw = response.getWriter();
			pw.write(gson.toJson(stocks));
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
