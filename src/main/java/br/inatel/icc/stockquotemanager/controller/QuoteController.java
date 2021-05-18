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
import br.inatel.icc.stockquotemanager.service.QuoteService;
import br.inatel.icc.stockquotemanager.service.StockService;

@RestController
@RequestMapping("/quote")
public class QuoteController {

	private QuoteService quoteService;
	private StockService stockService;
	
	@Autowired
	public QuoteController(QuoteService quoteService, StockService stockService) {
		this.quoteService = quoteService;
		this.stockService = stockService;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<StockQuoteDto> create(@RequestBody QuoteForm quoteForm, UriComponentsBuilder uriBuilder){
		StockDto stock = stockService.findById(quoteForm.getStockId());
		
		if(stock == null) {
			return ResponseEntity.status(404).build();
		}
		
		Quote quote = quoteForm.toQuote();
		
		StockQuoteDto stockQuote = quoteService.save(quote);
		
		URI uri = uriBuilder.path("/quote/{id}").buildAndExpand(quote.getId()).toUri();
		
		return ResponseEntity.status(201).location(uri).body(stockQuote);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StockQuoteDto> listById(@PathVariable("id") String stockId){
		StockDto stock = stockService.findById(stockId);
		
		if(stock == null) {
			return ResponseEntity.status(404).build();
		}
		
		StockQuoteDto stockQuote = quoteService.findByStockId(stockId);
		
		return ResponseEntity.status(200).body(stockQuote);
	}
	
	@GetMapping
	public ResponseEntity<List<StockQuoteDto>> list(){
		List<StockDto> stocks = stockService.findAll();
		
		List<StockQuoteDto> stockQuotes = stocks.stream().map(stock -> {
			StockQuoteDto stockQuote = quoteService.findByStockId(stock.getId());
			return stockQuote;
		}).collect(Collectors.toList());
		
		return ResponseEntity.status(200).body(stockQuotes);
	}
}
