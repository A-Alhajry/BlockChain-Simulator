package stocksanomaly.webapp.models;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StocksRepositoryInterface {
	
	List<StockAnomalyDataBean> getStocks(LocalDate startDate, LocalDate endDate, String companyName) throws Exception ;
	List<CompanySummary> getCompaniesSummaries() throws Exception ;
	void setDataSource(InputStream is);
}
