package br.inatel.icc.stockquotemanager.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.inatel.icc.stockquotemanager.model.Quote;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class QuoteRepositoryTest {

	@Autowired
	private QuoteRepository quoteRepository;
	
	@BeforeEach
	public void beforeEach() {
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(LocalDate.parse("2020-05-11"), new BigDecimal("200"), "petr3"));
		quotes.add(new Quote(LocalDate.parse("2021-01-10"), new BigDecimal("300"), "petr3"));
		quoteRepository.saveAll(quotes);
	}
	
	@Test
	public void shoudFindStockQuotesByStockId() {
		List<Quote> quotes = quoteRepository.findByStockId("petr3");
		
		Assert.assertEquals(2, quotes.size());
	}
}
