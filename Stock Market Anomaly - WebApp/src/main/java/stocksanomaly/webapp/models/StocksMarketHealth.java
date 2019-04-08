package stocksanomaly.webapp.models;

public enum StocksMarketHealth {
	
	NO_DATA(0),
	UNSTABLE(1),
	OK(2),
	STABLE(3);
	
	private int code;
	
	private StocksMarketHealth(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static StocksMarketHealth getByCode(int code) {
		
		StocksMarketHealth health = StocksMarketHealth.NO_DATA;
		
		for(StocksMarketHealth current : StocksMarketHealth.values()) {
			
			if (current.getCode() == code) {
				return current;
			}
		}
		
		return health;
	}
}
