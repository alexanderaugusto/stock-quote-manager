package br.inatel.icc.stockquotemanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.icc.stockquotemanager.dto.StockQuoteDto;
import br.inatel.icc.stockquotemanager.model.Quote;
import br.inatel.icc.stockquotemanager.repository.QuoteRepository;

@Service
public class QuoteService {

	private QuoteRepository quoteRepository;
	
	@Autowired
	public QuoteService(QuoteRepository quoteRepository) {
		this.quoteRepository = quoteRepository;
	}
	
	public StockQuoteDto saveAll(String stockId, List<Quote> quotes) {
		quoteRepository.saveAll(quotes);
		StockQuoteDto stockQuote = findByStockId(stockId);
		
		return stockQuote;
	}
	
	public StockQuoteDto findByStockId(String stockId) {
		List<Quote> quotes = quoteRepository.findByStockId(stockId);
		StockQuoteDto stockQuote = new StockQuoteDto(stockId, quotes);
		
		return stockQuote;
	}
	
	public Optional<Quote> findByStockIdAndDate(Quote quote) {
		return quoteRepository.findByStockIdAndDate(quote.getStockId(), quote.getDate());
	}
}
