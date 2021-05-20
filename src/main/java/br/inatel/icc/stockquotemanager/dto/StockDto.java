package br.inatel.icc.stockquotemanager.dto;

import java.util.List;

public class StockDto {

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public static boolean stockExists(String stockId, List<StockDto> stocks) {
		for (StockDto stock : stocks) {
			if(stock.getId().equals(stockId)) {
				return true;
			}
		}
		
		return false;
	}
}
