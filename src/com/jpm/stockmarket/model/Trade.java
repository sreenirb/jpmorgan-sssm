package com.jpm.stockmarket.model;

import java.util.Date;
public class Trade {
	private Stock stock = null;
	private TradeType tradeType = TradeType.BUY;	
	private int sharesQuantity = 0;
	private double price = 0.0;
	private Date timeStamp = null;
	
	public Trade(Stock stock, TradeType tradeType, int sharesQuantity,
			double price) {
		this.stock = stock;
		this.tradeType = tradeType;
		this.sharesQuantity = sharesQuantity;
		this.price = price;
		this.timeStamp = new Date();
	}
		
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public Trade(){
	}
		
	public int getSharesQuantity() {
		return sharesQuantity;
	}
		
	public void setSharesQuantity(int sharesQuantity) {
		this.sharesQuantity = sharesQuantity;
	}
		
	public TradeType gettradeType() {
		return tradeType;
	}
	
	public void settradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public double getPrice() {
		return price;
	}
	

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		String tradePattern = "Trade Object [timeStamp: %tF %tT, stock: %s, indicator: %s, shares quantity: %7d, price: %7.2f]";
		return String.format(tradePattern, timeStamp,timeStamp, stock, tradeType, sharesQuantity, price);
	}	
}
