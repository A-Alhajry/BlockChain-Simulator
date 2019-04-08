package stocksanomaly.webapp.models;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StocksRepositoryInterface {
	
	List<StockAnomalyDataBean> getStocks(LocalDate startDate, LocalDate endDate, String companyName) throws Exception ;
	List<CompanySummary> getCompaniesSummaries() throws Exception ;
	List<StocksMarket> getStocksMarkets() throws Exception;
	List<MarketHistory> getMarketsHistory() throws Exception;
	IndexStats getIndexStats() throws Exception;
	List<StocksPeriodicData> getYearlyData(int year) throws Exception;
}
