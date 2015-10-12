package com.jpm.stockmarket.servicestest;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpm.stockmarket.arch.StockMarketServiceFactory;
import com.jpm.stockmarket.entitymgr.EntityManager;
import com.jpm.stockmarket.model.Stock;
import com.jpm.stockmarket.model.StockType;
import com.jpm.stockmarket.model.Trade;
import com.jpm.stockmarket.model.TradeType;
import com.jpm.stockmarket.services.StockService;


public class StockServiceFactoryTesting {

	@Before
	public void populateTestData() throws Exception
	{
		    System.out.println("Test case 0: populateTestData Entering");
			StockMarketServiceFactory factoryInstance = StockMarketServiceFactory.INSTANCE;

			StockService simpleStockService = factoryInstance.getSimpleStockService();
			EntityManager etityManager = simpleStockService.getEntityManager();
			Stock stock = new Stock("TEA",StockType.COMMON, 0.0,0.0, 100.0);
			stock.setpricePerShare(100);
			etityManager.addStock("TEA", stock);
			Trade trade = new Trade(stock, TradeType.BUY, 100, 102.0);
			etityManager.recordTrade(trade);

			stock = new Stock("POP",StockType.COMMON, 8.0,0.0, 100.0);
			stock.setpricePerShare(100);
			etityManager.addStock("POP", stock);
			trade = new Trade(stock, TradeType.BUY, 100, 102.0);
			etityManager.recordTrade(trade);
			
			stock = new Stock("ALE",StockType.COMMON, 23.0,0.0, 60.0);
			stock.setpricePerShare(100);
			trade = new Trade(stock, TradeType.BUY, 100, 79.0);
			etityManager.recordTrade(trade);
			
			etityManager.addStock("ALE", stock);
			stock = new Stock("GIN",StockType.PREFERRED, 8.0,2, 100.0);
			stock.setpricePerShare(100);
			etityManager.addStock("GIN", stock);
			trade = new Trade(stock, TradeType.BUY, 100, 102.0);
			etityManager.recordTrade(trade);
			
			stock = new Stock("JOE",StockType.COMMON, 13.0,0.0, 250.0);
			stock.setpricePerShare(100);
			etityManager.addStock("JOE", stock);
			trade = new Trade(stock, TradeType.SELL, 100, 245.0);
			etityManager.recordTrade(trade);

			System.out.println("Test case 0: populateTestData Exiting");
			System.out.println("=============================");
			
	}
	
	@Test
	public void isStockMarketServiceFactoryASingleton(){

        System.out.println("Test case 1 : isStockServicesFactoryASingleton Entering");
		StockMarketServiceFactory factoryInstanceA = StockMarketServiceFactory.INSTANCE;
		StockMarketServiceFactory factoryInstanceB = StockMarketServiceFactory.INSTANCE;

		Assert.assertNotNull(factoryInstanceA);
		Assert.assertNotNull(factoryInstanceB);

		Assert.assertTrue(factoryInstanceA.equals(factoryInstanceB));
        System.out.println("Test case 1: isStockServicesFactoryASingleton Exiting");

	}

	@Test
	public void isStockServicesASingleton(){

   	    System.out.println("Test case 2: isStockServicesASingleton Entering");
		StockMarketServiceFactory factoryInstance = StockMarketServiceFactory.INSTANCE;

		StockService simpleStockServiceA = factoryInstance.getSimpleStockService();
		StockService simpleStockServiceB = factoryInstance.getSimpleStockService();

		Assert.assertNotNull(simpleStockServiceA);
		Assert.assertNotNull(simpleStockServiceB);

		Assert.assertTrue(simpleStockServiceA.equals(simpleStockServiceB));
        System.out.println("Test case 2: isStockServicesASingleton Exiting");
        
	}
	@Test
	public void recordATradeTest(){
		  System.out.println("Test case 3: recordATradeTest Entering");
		  try{
			StockMarketServiceFactory factoryInstance = StockMarketServiceFactory.INSTANCE;
			StockService simpleStockService = factoryInstance.getSimpleStockService();
			Assert.assertNotNull(simpleStockService);

			EntityManager etityManager = simpleStockService.getEntityManager();
			ArrayList<Trade> tradeList = etityManager.getTrades();
			int tradesNumber = tradeList.size();
			System.out.println("Trades number: "+tradesNumber);
			
			ArrayList<Trade> tradeList2 = new ArrayList(tradeList) ; 
			// re-Insert all trades in the stock system
				
			for(Trade trade: tradeList2){
				boolean result = etityManager.recordTrade(trade);
				Assert.assertTrue(result);
			}

			// After record trades, the number of trades should be equal to the trades list
			tradesNumber = etityManager.getTrades().size();
			System.out.println("Trades number: "+tradesNumber);		
		}catch(Exception exception){
			System.out.println(exception);			
		}

        System.out.println("Test case 3: recordATradeTest Exiting");


	} 
	@Test
	public void calculateDividendYieldTest(){
		  System.out.println("Test case 4: calculateDividendYieldTest Entering");

			try{
			StockMarketServiceFactory factoryInstance = StockMarketServiceFactory.INSTANCE;
			StockService simpleStockService = factoryInstance.getSimpleStockService();
			Assert.assertNotNull(simpleStockService);

			EntityManager etityManager = simpleStockService.getEntityManager();
			// Calculates the dividend yield for the stock symbol
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			//String[] stockSymbols = {"TEA"};
			for(String stockSymbol: stockSymbols){
				double dividendYield = simpleStockService.calculateDividendYield(stockSymbol);
				System.out.println(stockSymbol+" - DividendYield calculated: "+dividendYield);
				Assert.assertTrue(dividendYield >= 0.0);
			}

		}catch(Exception exception){
			System.out.println(exception);
			Assert.assertTrue(false);
		}

        System.out.println("Test case 4: calculateDividendYieldTest Exiting");

	}

    @Test
	public void calculatePERatioTest(){
		  System.out.println("Test case 5: calculatePERatioTest Entering");


		try{
			// Create the stock service and verify it's not null object
			StockMarketServiceFactory factoryInstance = StockMarketServiceFactory.INSTANCE;
			StockService simpleStockService = factoryInstance.getSimpleStockService();
			Assert.assertNotNull(simpleStockService);

			EntityManager etityManager = simpleStockService.getEntityManager();

			// Calculates the P/E Ratio for the stock Symbol
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			//String[] stockSymbols = {"TEA"};
			for(String stockSymbol: stockSymbols){
				double peRatio = simpleStockService.calculatePERatio(stockSymbol);
				 System.out.println(stockSymbol+" - P/E Ratio calculated: "+peRatio);
				Assert.assertTrue(peRatio >= 0.0);
			}
		}catch(Exception exception){
			 System.out.println(exception);
			Assert.assertTrue(false);
		}

		  System.out.println("Test case 5: calculatePERatioTest Exiting");
	}



    @Test
	public void calculateVolumeWeightedStockIndexTest(){
		 System.out.println("Test case 6: calculateVolumeWeightedStockIndexTest Entering");
		try{
			// Create the stock service and verify it's not null object
			StockMarketServiceFactory factoryInstance = StockMarketServiceFactory.INSTANCE;
			StockService simpleStockService = factoryInstance.getSimpleStockService();
			Assert.assertNotNull(simpleStockService);

			EntityManager etityManager = simpleStockService.getEntityManager();
			
			// Calculates the Stock Price for all stocks
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			for(String stockSymbol: stockSymbols){
				double stockPrice = simpleStockService.calculateStockPrice(stockSymbol);
				System.out.println(stockSymbol+" - Stock Price calculated: "+stockPrice);
				Assert.assertTrue(stockPrice >= 0.0);
			}

		}catch(Exception exception){
			System.out.println(exception);
			Assert.assertTrue(false);
		}
		  System.out.println("Test case 6: calculateVolumeWeightedStockIndexTest Exiting");

	}


	@Test
	public void calculateGBCEAllShareIndexTest(){
		System.out.println("Test case 7: calculateGBCEAllShareIndexTest Entering");

		try{
			// Create the stock service and verify it's not null object
			StockMarketServiceFactory factoryInstance = StockMarketServiceFactory.INSTANCE;
			StockService simpleStockService = factoryInstance.getSimpleStockService();
			Assert.assertNotNull(simpleStockService);

			EntityManager etityManager = simpleStockService.getEntityManager();
			int tradesNumber = etityManager.getTrades().size();
			System.out.println("Trades number: "+tradesNumber);
			
			double GBCEAllShareIndex = simpleStockService.calculateGBCEAllShareIndex();
			System.out.println("GBCE All Share Index: "+GBCEAllShareIndex);
			Assert.assertTrue(GBCEAllShareIndex > 0.0);
			
		}catch(Exception exception){
			System.out.println(exception);
			Assert.assertTrue(false);
		}
		 System.out.println("Test case 7: calculateGBCEAllShareIndexTest Entering");

	} 

} 
