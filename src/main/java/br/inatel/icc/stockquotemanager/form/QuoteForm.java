package br.inatel.icc.stockquotemanager.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.inatel.icc.stockquotemanager.model.Quote;

public class QuoteForm {

	private String id;
	private Map<String, String> quotes;

	public String getId() {
		return id;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}
	
	public List<Quote> convertQuotesMapToList(){
		List<Quote> quotesList = new ArrayList<>();
		
		for (Map.Entry<String, String> quote : this.quotes.entrySet()) {
			LocalDate date = LocalDate.parse(quote.getKey());
			BigDecimal value = new BigDecimal(quote.getValue());
			
		    quotesList.add(new Quote(date, value, this.id));
		}
		
		return quotesList;
	}
}
