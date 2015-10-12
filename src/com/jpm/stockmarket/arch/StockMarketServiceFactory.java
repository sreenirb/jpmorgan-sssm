package com.jpm.stockmarket.arch;

import com.jpm.stockmarket.arch.impl.StockMarketServiceFactoryImpl;
import com.jpm.stockmarket.services.StockService;

public interface StockMarketServiceFactory {
	/**
	 * Singleton instance of the factory StockMarketServiceFactory.
	 */
	public StockMarketServiceFactory INSTANCE = StockMarketServiceFactoryImpl.getInstance();
	
	/**
	 * Gets the singleton instance of the stock Service, which implements the five operations
	 * to calculate the dividend yield, P/E Ratio, Stock Price, GBCE All Share Index and record trades 
	 * for a given stock.
	 */
	public StockService getSimpleStockService();

}
