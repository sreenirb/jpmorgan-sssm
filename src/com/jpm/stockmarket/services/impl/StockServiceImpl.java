package com.jpm.stockmarket.services.impl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.math3.stat.StatUtils;
import com.jpm.stockmarket.entitymgr.EntityManager;
import com.jpm.stockmarket.model.Stock;
import com.jpm.stockmarket.model.Trade;
import com.jpm.stockmarket.services.StockService;



public class StockServiceImpl implements StockService{

	private EntityManager entityManager = null;

	public StockServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
		}

	public EntityManager getEntityManager() throws Exception {		
		return entityManager;
	}

	public double calculateDividendYield(String stockSymbol) throws Exception{
		double dividendYield = -1.0;

		try{
			Stock stock = entityManager.getStockBySymbol(stockSymbol);
		
			if(stock==null){
				throw new Exception("The stock symbol ["+stockSymbol+"] is not supported ");
			}
			if(stock.getpricePerShare() <= 0.0){
				throw new Exception("The ticker price for the stock ["+stockSymbol+"] should be greater than zero (0).");
			}
			dividendYield = stock.getDividendYield();


		}catch(Exception exception){
			throw new Exception("Error calculating Dividend Yield for the stock symbol: "+stockSymbol+".", exception);
		}
		return dividendYield;
	}


	public double calculatePERatio(String stockSymbol) throws Exception{
		double peRatio = -1.0;
		try{
			Stock stock = entityManager.getStockBySymbol(stockSymbol);

			if(stock==null){
				throw new Exception("The stock symbol ["+stockSymbol+"] is not supported ");
			}
			if(stock.getpricePerShare() <= 0.0){
				throw new Exception("The ticker price for the stock ["+stockSymbol+"] should be greater than zero (0).");
			}
			peRatio = stock.getPeRatio();

		}catch(Exception exception){
			throw new Exception("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);
		}
		return peRatio;
	}


	public boolean recordTrade(Trade trade) throws Exception{
		boolean recordResult = false;
		try{
			
		// trade should be an object
			if(trade==null){
				throw new Exception("Trade object to record should be a valid object and it's null.");
			}

			// stock should be an object
			Stock stock = trade.getStock();
			if(stock == null)
			{
				throw new Exception("A trade should be associated with a stock and the stock for the trade is null.");
			}

			// shares quantity should be greater than zero
			if(trade.getSharesQuantity()<=0){
				throw new Exception("Shares quantity in the trade to record should be greater than zero.");
			}

			// shares price should be greater than zero
			Double sharePrice = trade.getPrice();
			if(sharePrice<=0.0){
				throw new Exception("Shares price in the trade to record should be greater than zero.");
			}

			recordResult = entityManager.recordTrade(trade);

			// Update the ticker price for the stock
			if(recordResult)
			{
				stock.setpricePerShare(sharePrice);	
				entityManager.addStock(stock.getStockSymbol(), stock);
			}


		}catch(Exception exception){
			throw new Exception("Error when trying to record a trade.", exception);
		}
		return recordResult;
	}

private class StockPredicate implements Predicate{
		
		private String stockSymbol = "";
		
		private Calendar dateRange = null;
		
		public StockPredicate(String stockSymbol, int minutesRange){
			this.stockSymbol = stockSymbol;
			if( minutesRange > 0 ){
				dateRange = Calendar.getInstance();
				dateRange.add(Calendar.MINUTE, -minutesRange);
			}
		}


		public boolean evaluate(Object tradeObject) {
			Trade trade = (Trade) tradeObject;
			boolean shouldBeInclude = trade.getStock().getStockSymbol().equals(stockSymbol);
			if(shouldBeInclude && dateRange != null){
				shouldBeInclude = dateRange.getTime().compareTo(trade.getTimeStamp())<=0;
			}
			return shouldBeInclude;
		}

	}

	private double calculateStockPriceinRange(String stockSymbol, int minutesRange) throws Exception{
		double stockPrice = 0.0;
		
		@SuppressWarnings("unchecked")
		Collection<Trade> trades = CollectionUtils.select(entityManager.getTrades(), new StockPredicate(stockSymbol, minutesRange));

		// Calculate the summation
		double shareQuantityAcum = 0.0;
		double tradePriceAcum = 0.0;
		for(Trade trade : trades){
			// Calculate the summation of Trade Price x Quantity
			tradePriceAcum += (trade.getPrice() * trade.getSharesQuantity());
			// Acumulate Quantity
			shareQuantityAcum += trade.getSharesQuantity();
		}

		// calculate the stock price
		if(shareQuantityAcum > 0.0){
			stockPrice = tradePriceAcum / shareQuantityAcum;	
		}


		return stockPrice;
	}

	public double calculateStockPrice(String stockSymbol) throws Exception{
		double stockPrice = 0.0;

		try{
			Stock stock = entityManager.getStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if(stock==null){
				throw new Exception("The stock symbol ["+stockSymbol+"] is not supported");
			}

			stockPrice = calculateStockPriceinRange(stockSymbol, 15);

		}catch(Exception exception){
			throw new Exception("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);
		}


		return stockPrice;
	}

	public double calculateGBCEAllShareIndex() throws Exception{
		double allShareIndex = 0.0;
		
		// Calculate stock price for all stock in the system
		HashMap<String, Stock> stocks = entityManager.getStocks();
		ArrayList<Double> stockPrices = new ArrayList<Double>();
		for(String stockSymbol: stocks.keySet() ){
			double stockPrice = calculateStockPriceinRange(stockSymbol, 0);
			if(stockPrice > 0){
				stockPrices.add(stockPrice);
			}
		}
		
		if(stockPrices.size()>=1){
			double[] stockPricesArray = new double[stockPrices.size()];
			
			for(int i=0; i<=(stockPrices.size()-1); i++){
				stockPricesArray[i] = stockPrices.get(i).doubleValue();
			}
			// Calculates the GBCE All Share Index
			allShareIndex = StatUtils.geometricMean(stockPricesArray);

		}
		return allShareIndex;
	}


	public int getTradesNumber() throws Exception {
		return entityManager.getTrades().size();
	}
}
