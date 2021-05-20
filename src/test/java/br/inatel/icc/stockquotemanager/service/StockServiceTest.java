package br.inatel.icc.stockquotemanager.service;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.inatel.icc.stockquotemanager.dto.StockDto;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class StockServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private StockService stockService;
	
	private StockDto[] stocks = new StockDto[2];
	
	@BeforeEach
	public void beforeEach() {
		StockDto stock1 = new StockDto();
		stock1.setId("petr4");
		
		StockDto stock2 = new StockDto();
		stock2.setId("vale5");
		
		stocks[0] = stock1;
		stocks[1] = stock2;
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldFindAllStocks() {
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenReturn(stocks);
		
		List<StockDto> stocksDto = stockService.findAll();
		
		Assert.assertEquals(stocks.length, stocksDto.size());
	}

}
