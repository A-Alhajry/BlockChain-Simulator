package stocksanomaly.webapp.models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class StocksRepository implements StocksRepositoryInterface {
	
	private List<StockAnomalyDataBean> stocks;
	private InputStream dataSource;
	
	public List<StockAnomalyDataBean> getStocks(LocalDate startDate, LocalDate endDate, String companyName) throws Exception {
		List<StockAnomalyDataBean> stocks = new ArrayList<StockAnomalyDataBean>();
		
		DecimalFormat valueFormat = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		valueFormat.setDecimalFormatSymbols(symbols);
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("StocksDump.txt")));
		
		String line = reader.readLine();
		StockAnomalyDataBean previousBean = null;
		while((line = reader.readLine()) != null ) {
			String[] values = line.split(",");
			long id = 0l;
			String name = "";
			String company = values[4];
			LocalDate date = LocalDate.parse(values[0], dateFormat);
			double actualValue = Double.parseDouble(values[1]);
			double forecastedValue = Double.parseDouble(values[2]);
			boolean isAnomaly = Boolean.parseBoolean(values[3]);
			
			if ( date.isBefore(startDate) || date.isAfter(endDate)) {
				continue;
			}
			
			previousBean = new StockAnomalyDataBean(0, name, company, date, forecastedValue, actualValue, isAnomaly, previousBean);
			stocks.add(previousBean);
		}
		
		return stocks;
		
	}
	
	public List<CompanySummary> getCompaniesSummaries() throws Exception{
		List<CompanySummary> result = new ArrayList<>();
		
		List<StockAnomalyDataBean> stocksData = getStocks(LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 31), "qp");
		
		CompanySummary qp = new CompanySummary();
		CompanySummary qib = new CompanySummary();
		CompanySummary qnb = new CompanySummary();
		CompanySummary vodafone = new CompanySummary();
		CompanySummary ooredoo = new CompanySummary();
		CompanySummary mannai = new CompanySummary();
		
		qp.setFullName("Qatar Petroleum");
		qp.setShortName("QP");
		qp.setIconUrl("qp.jpg");
		qp.setIndicator(StockIndicator.UP);
		qp.setActualValue(stocksData.get(0).getActualValue());
		qp.setForecastedValue(stocksData.get(0).getForecastedValue());
		qp.setDifferenceValue(stocksData.get(0).getDifference());
		qp.setDifferencePercentage(stocksData.get(0).getPercentage());
		qp.setDate(stocksData.get(0).getDate());
		
		qib.setFullName("Qatar Islamic Bank");
		qib.setShortName("QIB");
		qib.setIconUrl("qib.jpg");
		qib.setIndicator(StockIndicator.DOWN);
		qib.setActualValue(stocksData.get(1).getActualValue());
		qib.setForecastedValue(stocksData.get(1).getForecastedValue());
		qib.setDifferenceValue(stocksData.get(1).getDifference());
		qib.setDifferencePercentage(stocksData.get(1).getPercentage());
		qib.setDate(stocksData.get(1).getDate());
		
		qnb.setFullName("Qatar National Bank");
		qnb.setShortName("QNB");
		qnb.setIconUrl("qnb.jpg");
		qnb.setIndicator(StockIndicator.NO_CHANGE);
		qnb.setActualValue(stocksData.get(2).getActualValue());
		qnb.setForecastedValue(stocksData.get(2).getForecastedValue());
		qnb.setDifferenceValue(stocksData.get(2).getDifference());
		qnb.setDifferencePercentage(stocksData.get(2).getPercentage());
		qnb.setDate(stocksData.get(2).getDate());
		
		vodafone.setFullName("Vodafone Qatar");
		vodafone.setShortName("Vodafone");
		vodafone.setIconUrl("vodafone.jpg");
		vodafone.setIndicator(StockIndicator.NO_CHANGE);
		vodafone.setActualValue(stocksData.get(3).getActualValue());
		vodafone.setForecastedValue(stocksData.get(3).getForecastedValue());
		vodafone.setDifferenceValue(stocksData.get(3).getDifference());
		vodafone.setDifferencePercentage(stocksData.get(3).getPercentage());
		vodafone.setDate(stocksData.get(3).getDate());
		
		ooredoo.setFullName("Ooredoo Qatar");
		ooredoo.setShortName("Ooredoo");
		ooredoo.setIconUrl("ooredoo.jpg");
		ooredoo.setIndicator(StockIndicator.UP);
		ooredoo.setActualValue(stocksData.get(4).getActualValue());
		ooredoo.setForecastedValue(stocksData.get(4).getForecastedValue());
		ooredoo.setDifferenceValue(stocksData.get(4).getDifference());
		ooredoo.setDifferencePercentage(stocksData.get(4).getPercentage());
		ooredoo.setDate(stocksData.get(4).getDate());
		
		mannai.setFullName("Mannai Corporation");
		mannai.setShortName("Manni");
		mannai.setIconUrl("mannai.jpg");
		mannai.setIndicator(StockIndicator.DOWN);
		mannai.setActualValue(stocksData.get(5).getActualValue());
		mannai.setForecastedValue(stocksData.get(5).getForecastedValue());
		mannai.setDifferenceValue(stocksData.get(5).getDifference());
		mannai.setDifferencePercentage(stocksData.get(5).getPercentage());
		mannai.setDate(stocksData.get(5).getDate());

		
		result.add(mannai);
		result.add(ooredoo);
		result.add(vodafone);
		result.add(qnb);
		result.add(qib);
		result.add(qp);

		return result;
		
	}
	
	public void setDataSource(InputStream dataSource) {
		this.dataSource = dataSource;
	}
	
	
}


