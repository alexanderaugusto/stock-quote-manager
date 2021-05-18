package br.inatel.icc.stockquotemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.inatel.icc.stockquotemanager.model.Quote;

public class StockQuoteDto {

	private String id;
	private Map<String, String> quotes = new HashMap<>();
	
	public StockQuoteDto(String stockId, List<Quote> quotes) {
		this.id = stockId;
		this.quotes = convertQuotesListToMap(quotes);
	}
	
	public String getId() {
		return id;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}

	public Map<String, String> convertQuotesListToMap(List<Quote> quotesList){
		Map<String, String> quotesMap = new HashMap<>();
		
		quotesList.forEach(quote -> {
			LocalDate date = quote.getDate();
			BigDecimal value = quote.getValue();
			
			quotesMap.put(date.toString(), value.toBigInteger().toString());
		});
		
		return quotesMap;
	}
}
