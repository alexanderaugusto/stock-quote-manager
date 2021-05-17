package br.inatel.icc.stockquotemanager.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.inatel.icc.stockquotemanager.model.Quote;

public class QuoteForm {

	private String date;
	private String value;
	private String stockId;
	
	public String getDate() {
		return date;
	}

	public String getValue() {
		return value;
	}

	public String getStockId() {
		return stockId;
	}

	public Quote toQuote() {
		LocalDate date = LocalDate.parse(this.date);
		BigDecimal value = new BigDecimal(this.value);
		
		Quote quote = new Quote(date, value, stockId);
		
		return quote;
	}
}
