package br.inatel.icc.stockquotemanager.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.icc.stockquotemanager.dto.FormErrorDto;
import br.inatel.icc.stockquotemanager.dto.StockDto;
import br.inatel.icc.stockquotemanager.dto.StockQuoteDto;
import br.inatel.icc.stockquotemanager.exceptions.FieldConflictException;
import br.inatel.icc.stockquotemanager.form.QuoteForm;
import br.inatel.icc.stockquotemanager.model.Quote;
import br.inatel.icc.stockquotemanager.service.QuoteService;
import br.inatel.icc.stockquotemanager.service.StockService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/quote")
@Slf4j
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
	public ResponseEntity<StockQuoteDto> create(@RequestBody @Valid QuoteForm quoteForm,
			UriComponentsBuilder uriBuilder) throws FieldConflictException {
		List<StockDto> stocks = stockService.findAll();

		if (!StockDto.stockExists(quoteForm.getId(), stocks)) {
			log.error("stock of id " + quoteForm.getId() + " not found");
			return ResponseEntity.status(404).build();
		}

		List<Quote> quotes = quoteForm.convertQuotesMapToList();

		for (Quote quote : quotes) {
			if (quoteService.findByStockIdAndDate(quote).isPresent()) {
				throw new FieldConflictException(new FormErrorDto("quotes",
						"Already exist a value to date " + quote.getDate() + " in stock " + quoteForm.getId()));
			}
		}

		StockQuoteDto stockQuote = quoteService.saveAll(quoteForm.getId(), quotes);

		URI uri = uriBuilder.path("/quote/{id}").buildAndExpand(stockQuote.getId()).toUri();

		log.info(quotes.size() + " quote(s) was created to the stock " + quoteForm.getId());

		return ResponseEntity.status(201).location(uri).body(stockQuote);
	}

	@GetMapping("/{id}")
	public ResponseEntity<StockQuoteDto> listById(@PathVariable("id") String stockId) {
		List<StockDto> stocks = stockService.findAll();

		if (!StockDto.stockExists(stockId, stocks)) {
			log.error("stock of id " + stockId + " not found");
			return ResponseEntity.status(404).build();
		}

		StockQuoteDto stockQuote = quoteService.findByStockId(stockId);

		log.info("listing quotes of stock " + stockId);

		return ResponseEntity.status(200).body(stockQuote);
	}

	@GetMapping
	public ResponseEntity<List<StockQuoteDto>> list() {
		List<StockDto> stocks = stockService.findAll();

		List<StockQuoteDto> stockQuotes = stocks.stream().map(stock -> {
			StockQuoteDto stockQuote = quoteService.findByStockId(stock.getId());
			return stockQuote;
		}).collect(Collectors.toList());

		log.info("listing all stock quotes: " + stockQuotes.size() + " stocks");

		return ResponseEntity.status(200).body(stockQuotes);
	}
}
