package br.inatel.icc.stockquotemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.inatel.icc.stockquotemanager.model.Quote;

public class StockQuoteDto {

	private String id;
	private Map<String, String> quotes = new HashMap<String, String>();
	
	public StockQuoteDto(String stockId, List<Quote> quotes) {
		this.id = stockId;
		convertQuotes(quotes);
	}
	
	public String getId() {
		return id;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}

	private void convertQuotes(List<Quote> quotes) {
		quotes.forEach(quote -> {
			LocalDate date = quote.getDate().toLocalDate();
			BigDecimal value = quote.getValue();
			
			this.quotes.put(date.toString(), value.toBigInteger().toString());
		});
	}
}
