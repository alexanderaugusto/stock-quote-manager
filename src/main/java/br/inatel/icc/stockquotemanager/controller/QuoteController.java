package br.inatel.icc.stockquotemanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.icc.stockquotemanager.dto.StockDto;
import br.inatel.icc.stockquotemanager.dto.StockQuoteDto;
import br.inatel.icc.stockquotemanager.model.Quote;
import br.inatel.icc.stockquotemanager.repository.QuoteRepository;
import br.inatel.icc.stockquotemanager.service.StockService;

@RestController
@RequestMapping("/quote")
public class QuoteController {

	private QuoteRepository quoteRepository;
	
	@Autowired
	public QuoteController(QuoteRepository quoteRepository) {
		this.quoteRepository = quoteRepository;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StockQuoteDto> listById(@PathVariable("id") String stockId){
		StockService stockService = new StockService();
		StockDto stock = stockService.getById(stockId);
		
		if(stock == null) {
			return ResponseEntity.status(404).build();
		}
		
		List<Quote> quotes = quoteRepository.findByStockId(stockId);
		StockQuoteDto stockQuote = new StockQuoteDto(stockId, quotes);
		return ResponseEntity.status(200).body(stockQuote);
	}
	
	@GetMapping
	public ResponseEntity<List<StockQuoteDto>> list(){
		StockService stockService = new StockService();
		List<StockDto> stocks = stockService.getAll();
		
		List<StockQuoteDto> stockQuotes = stocks.stream().map(stock -> {
			List<Quote> quotes = quoteRepository.findByStockId(stock.getId());
			StockQuoteDto stockQuote = new StockQuoteDto(stock.getId(), quotes);
			return stockQuote;
		}).collect(Collectors.toList());
		
		return ResponseEntity.status(200).body(stockQuotes);
	}
}
