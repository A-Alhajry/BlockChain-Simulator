package stocksanomaly.webapp.models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
	
	public List<StocksMarket> getStocksMarkets() throws Exception {
		List<StocksMarket> markets = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("MarketsDump.txt")));
		String line = reader.readLine();
		while ((line = reader.readLine()) != null) {
			String[] row = line.split(",");
			int id = Integer.parseInt(row[0]);
			String name = row[1];
			String indexName = row[2];
			String logo = row[3];
			double actual = Double.parseDouble(row[4]);
			double forecasted = Double.parseDouble(row[5]);
			int stocks = Integer.parseInt(row[6]);
			int anomals = Integer.parseInt(row[7]);
			boolean isOpen = Boolean.parseBoolean(row[8]);
			StocksMarketHealth health = StocksMarketHealth.getByCode(Integer.parseInt(row[9]));
			
			StocksMarket market = new StocksMarket();
			market.setId(id);
			market.setName(name);
			market.setLogo(logo);
			market.setIndexName(indexName);
			market.setActualIndex(actual);
			market.setForecastedIndex(forecasted);
			market.setTotalStocks(stocks);
			market.setTotalAnomals(anomals);
			market.setIsOpen(isOpen);
			market.setHealth(health);
			
			markets.add(market);
		}
		
		return markets;
	}
	
	public List<MarketHistory> getMarketsHistory() throws Exception {
		List<MarketHistory> histories = new ArrayList<>();
		
		List<StocksMarket> markets = this.getStocksMarkets();
		
		for(int i = 0; i < markets.size(); i++) {
			StocksMarket market = markets.get(i);
			MarketHistory history = new MarketHistory();
			MarketSummary today = new MarketSummary();
			MarketSummary yesterday = new MarketSummary();
			List<MarketSummary> summary = new ArrayList<>();
			
			history.setName(market.getName());
			history.setId(market.getId());
			history.setHistory(summary);
			
			today.setDate(LocalDate.now());
			today.setDateString("Today");
			today.setTotalAnomaly(market.getTotalAnomals());
			yesterday.setDate(LocalDate.now().minusDays(1));
			yesterday.setDateString("Yesterday");
			
			if ((i % 2) == 0) {
				yesterday.setTotalAnomaly(market.getTotalAnomals() - 5);
			}
			
			else {
				yesterday.setTotalAnomaly(market.getTotalAnomals() + 5);
			}
			
			
			
			summary.add(today);
			summary.add(yesterday);
			
			histories.add(history);
			
			
			
		}
		return histories;
	}
	
	public IndexStats getIndexStats() throws Exception {
		
		IndexStats result = new IndexStats();
		result.setSource("All");
		result.setSubStats(new ArrayList<>());
		result.setStocks(150);
		result.setRegulars(130);
		result.setAnomals(20);
		result.setRegularsPerc(86);
		result.setAnomalsPerc(14);
		
		List<StocksMarket> markets = this.getStocksMarkets();
		int totalStocks = 0;
		int totalAnomals = 0;
		int totalRegulars = 0;
		int totalStables = 0;
		int totalUnstables = 0;
		int totalOk = 0;
		int totalUnknowns = 0;
		
		for(StocksMarket market : markets) {
			IndexStats sub = new IndexStats();
			sub.setSource(market.getIndexName());
			sub.setStocks(market.getTotalStocks());
			sub.setRegulars(market.getTotalStocks() - market.getTotalAnomals());
			sub.setAnomals(market.getTotalAnomals());
			double regPerc = (double) sub.getRegulars() / sub.getStocks() * 100.0;
			double anmPerc = (double) sub.getAnomals() / sub.getStocks() * 100.0;
			
			totalStocks += sub.getStocks();
			totalAnomals += sub.getAnomals();
			totalRegulars += sub.getRegulars();
			
			if (market.getHealth() == StocksMarketHealth.STABLE) {
				totalStables++;
			}
			
			else if (market.getHealth() == StocksMarketHealth.OK) {
				totalOk++;
			}
			
			else if (market.getHealth() == StocksMarketHealth.UNSTABLE) {
				totalUnstables++;
			}
			
			else {
				totalUnknowns++;
			}
			
			result.setStables(totalStables);
			result.setOk(totalOk);
			result.setUnstables(totalUnstables);
			result.setUnknowns(totalUnknowns);
			
			sub.setRegularsPerc((int) regPerc);
			sub.setAnomalsPerc((int) anmPerc);
			result.getSubStats().add(sub);
		}
		
		result.setStocks(totalStocks);
		result.setRegulars(totalRegulars);
		result.setAnomals(totalAnomals);

		return result;
		
	}
	
	public List<StocksPeriodicData> getYearlyData(int year) throws Exception{
		
		Map<Integer, StocksPeriodicData> result = new HashMap<Integer, StocksPeriodicData>();
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year, 12, 31);
		List<StockAnomalyDataBean> stocks = this.getStocks(startDate, endDate, "qp");
		Map<Integer, Integer> regularsCount = new HashMap<Integer, Integer>();
		Map<Integer, Integer> anomalsCount = new HashMap<Integer, Integer>();
		
		
		for(StockAnomalyDataBean stock : stocks) {
			int month = stock.getDate().getMonthValue();
			
			if (!regularsCount.containsKey(month)) {
				regularsCount.put(month, 0);
			}
			
			if (!anomalsCount.containsKey(month)) {
				anomalsCount.put(month, 0);
			}
			
			int regulars = regularsCount.get(month);
			int anomals = anomalsCount.get(month);
			
			if (!stock.getIsAnomaly()) {
				regularsCount.put(month, regulars + 1);
			}
			
			else {
				anomalsCount.put(month, anomals + 1);
			}
		}
		
		for(int i = 1; i <= 12; i++) {
			Month month = Month.of(i);
			StocksPeriodicData data = new StocksPeriodicData();
			data.setLabel(month.getDisplayName(TextStyle.FULL, Locale.getDefault()));
			result.put(i, data);
		}
		
		Iterator rt = regularsCount.entrySet().iterator();
		while(rt.hasNext()) {
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) rt.next();
			StocksPeriodicData data = result.get(entry.getKey());
			data.setRegularsCount(entry.getValue());
			rt.remove();
		}
		
		Iterator at = anomalsCount.entrySet().iterator();
		while(at.hasNext()) {
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) at.next();
			StocksPeriodicData data = result.get(entry.getKey());
			data.setAnomalsCount(entry.getValue());
		}
		
		return new ArrayList<StocksPeriodicData>(result.values());
	}
	
}


