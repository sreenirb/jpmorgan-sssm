package com.jpm.stockmarket.services;

import com.jpm.stockmarket.entitymgr.EntityManager;
import com.jpm.stockmarket.model.Trade;;

public interface StockService {
	
	public double calculateDividendYield(String stockSymbol) throws Exception;
	public double calculatePERatio(String stockSymbol) throws Exception;
	public boolean recordTrade(Trade trade) throws Exception;
	public double calculateStockPrice(String stockSymbol) throws Exception;
	public double calculateGBCEAllShareIndex() throws Exception;
	public EntityManager getEntityManager() throws Exception;
}
