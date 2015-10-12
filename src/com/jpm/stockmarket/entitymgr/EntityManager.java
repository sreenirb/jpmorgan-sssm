package com.jpm.stockmarket.entitymgr;

import java.util.ArrayList;
import java.util.HashMap;
import com.jpm.stockmarket.model.*;

public interface EntityManager {
	
	public boolean recordTrade(Trade trade) throws Exception;
	public ArrayList<Trade> getTrades();
	public Stock getStockBySymbol(String stockSymbol);
	public HashMap<String, Stock> getStocks();
	public Stock removeStock(String stock);	
	public void addStock(String stockSymbol, Stock stock);
}
