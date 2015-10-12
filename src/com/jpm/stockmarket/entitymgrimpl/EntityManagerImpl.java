package com.jpm.stockmarket.entitymgrimpl;

import java.util.ArrayList;
import java.util.HashMap;
import com.jpm.stockmarket.model.*;
import com.jpm.stockmarket.entitymgr.*;


public class EntityManagerImpl implements EntityManager{

	private HashMap<String, Stock> stocks = null; // no duplicates are allowed for stock and its contents


	private ArrayList<Trade> trades = null; // duplicates are allowed for trades

	public EntityManagerImpl(){
		trades = new ArrayList<Trade>();
		stocks = new HashMap<String, Stock>();
	}

	public HashMap<String, Stock> getStocks() {
		return stocks;
	}

	public void setStocks(HashMap<String, Stock> stocks) {
		this.stocks = stocks;
	}
	
	public Stock removeStock(String stock) {
		return stocks.remove(stock);
	}

	public void addStock(String stockSymbol, Stock stock) {
		this.stocks.put(stockSymbol, stock);
	}

	public ArrayList<Trade> getTrades() {
		return trades;
	}
	
	public void setTrades(ArrayList<Trade> trades) {
		this.trades = trades;
	}

	public synchronized boolean  recordTrade(Trade trade) throws Exception{
		boolean result = false;
		try{
			
			result = trades.add(trade);
		}catch(Exception exception){
			throw new Exception("An error has occurred during add trade", exception);
		}
		return result;
	}
	
	public int getTradesNumber() {
		return trades.size();
	}

	public Stock getStockBySymbol(String stockSymbol){
		Stock stock = null;
		try{
			if(stockSymbol!=null){
				stock = stocks.get(stockSymbol);
			}
		}catch(Exception exception){

		}
		return stock;
	}
}
