package com.jpm.stockmarket.model;

/**
 * @author Sreeni
 *
 */
public class Stock {

	private String stockSymbol = null;	
	private StockType stockType = StockType.COMMON;
	private double lastDividend = 0.0;
	private double fixedDividend = 0.0;
	private double parValue = 0.0;	
	private double pricePerShare = 0.0; 
	
	public Stock(String stockSymbol, StockType stockType, double lastDividend,
			double fixedDividend,  double parValue) {
		
		this.stockSymbol = stockSymbol;
		this.stockType = stockType;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

	
	
	public Stock(){		
	}
	
	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public StockType getStockType() {
		return stockType;
	}


	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public double getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}

	public double getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public double getParValue() {
		return parValue;
	}

	public void setParValue(double parValue) {
		this.parValue = parValue;
	}

	public double getpricePerShare() {
		return pricePerShare;
	}


	public void setpricePerShare(double pricePerShare) {
		this.pricePerShare = pricePerShare;
	}
	
	public double getDividendYield() {
		double dividendYield = -1.0;  
		if(pricePerShare > 0.0){
			if( stockType==StockType.COMMON){
				dividendYield = lastDividend / pricePerShare;
			}else{			
				dividendYield = (fixedDividend * parValue ) / pricePerShare;
			}
		}
		return dividendYield;
	}


	public double getPeRatio() {
		double peRatio = -1.0;  
		
		if(pricePerShare > 0.0){
			peRatio = pricePerShare / getDividendYield();	
		}
		
		return peRatio;
	}
	
	@Override
	public String toString() {
		String pattern = "Stock Object [stockSymbol: %s, stockType: %s, lastDividend: %7.2f, fixedDividend: %7.2f, parValue: %7.2f]";
		return String.format(pattern, stockSymbol, stockType, lastDividend, fixedDividend, parValue);
	}
}
