package br.inatel.icc.stockquotemanager.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.icc.stockquotemanager.dto.StockDto;
import br.inatel.icc.stockquotemanager.dto.StockQuoteDto;
import br.inatel.icc.stockquotemanager.form.QuoteForm;
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
	
	@PostMapping
	@Transactional
	public ResponseEntity<StockQuoteDto> create(@RequestBody QuoteForm quoteForm, UriComponentsBuilder uriBuilder){
		StockService stockService = new StockService();
		StockDto stock = stockService.getById(quoteForm.getStockId());
		
		if(stock == null) {
			return ResponseEntity.status(404).build();
		}
		
		Quote quote = quoteForm.toQuote();
		
		quoteRepository.save(quote);
		
		List<Quote> quotes = quoteRepository.findByStockId(quote.getStockId());
		StockQuoteDto stockQuote = new StockQuoteDto(quote.getStockId(), quotes);
		
		URI uri = uriBuilder.path("/quote/{id}").buildAndExpand(quote.getId()).toUri();
		
		return ResponseEntity.status(201).location(uri).body(stockQuote);
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
