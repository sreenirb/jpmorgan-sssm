package com.jpm.stockmarket.arch.impl;


import com.jpm.stockmarket.arch.StockMarketServiceFactory;
import com.jpm.stockmarket.entitymgrimpl.EntityManagerImpl;
import com.jpm.stockmarket.services.StockService;
import com.jpm.stockmarket.services.impl.StockServiceImpl;

public class StockMarketServiceFactoryImpl implements StockMarketServiceFactory {
	
	private static  final StockMarketServiceFactory INSTANCE =  new StockMarketServiceFactoryImpl();
    private static final StockService stockService = new StockServiceImpl(new EntityManagerImpl());

		private StockMarketServiceFactoryImpl() {
		// TODO Auto-generated constructor stub
		}

		public static StockMarketServiceFactory getInstance(){			
			return INSTANCE;
		}

		@Override
		public StockService getSimpleStockService() {			
			return stockService;
		}
	}


	
