package br.inatel.icc.stockquotemanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.inatel.icc.stockquotemanager.dto.StockQuoteDto;
import br.inatel.icc.stockquotemanager.model.Quote;
import br.inatel.icc.stockquotemanager.repository.QuoteRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class QuoteServiceTest {

	private QuoteService quoteService;
	
	@MockBean
	private QuoteRepository quoteRepository;
	
	private List<Quote> quotes;
	private String stockId = "petr3";
	
	@BeforeEach
	public void beforeEach() {
		this.quoteService = new QuoteService(quoteRepository);
		quotes = new ArrayList<>();
		quotes.add(new Quote(LocalDate.parse("2020-05-11"), new BigDecimal("200"), stockId));
		quotes.add(new Quote(LocalDate.parse("2021-01-10"), new BigDecimal("300"), stockId));
	}
	
	@Test
	public void shouldCreateQuotesToAStock() {
		Mockito.when(quoteRepository.saveAll(quotes)).thenReturn(null);
		Mockito.when(quoteRepository.findByStockId(stockId)).thenReturn(quotes);
		StockQuoteDto stockQuote = quoteService.saveAll(stockId, quotes);
		Assert.assertEquals(quotes.size(), stockQuote.getQuotes().size());
	}
	
	@Test
	public void shoudFindStockQuotesByStockId() {
		Mockito.when(quoteRepository.findByStockId(stockId)).thenReturn(quotes);
		StockQuoteDto stockQuote = quoteService.findByStockId(stockId);
		Assert.assertEquals(quotes.size(), stockQuote.getQuotes().size());
	}
}
