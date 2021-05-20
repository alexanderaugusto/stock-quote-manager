package br.inatel.icc.stockquotemanager.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import br.inatel.icc.stockquotemanager.dto.StockDto;
import br.inatel.icc.stockquotemanager.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuoteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StockService stockService;
	
	private List<StockDto> stocks;
	
	@BeforeEach
	public void beforeEach() {
		stocks = new ArrayList<>();
		
		StockDto stock1 = new StockDto();
		stock1.setId("vale5");
		
		StockDto stock2 = new StockDto();
		stock2.setId("petr4");
		
		stocks.add(stock1);
		stocks.add(stock2);
		
		Mockito.when(stockService.findAll()).thenReturn(stocks);
	}
	
	@Test
	public void shouldListStockQuoteByStockId() throws Exception {
		String stockId = "petr4";
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/quote/" + stockId)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(200))
		.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
		.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
	}
	
	@Test
	public void shouldNotListStockQuoteBecauseStockNotExists() throws Exception {
		String stockId = "petr1";
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/quote/" + stockId)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(404));
	}

	@Test
	public void shouldListAllStockQuotes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/quote")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(200))
		.andExpect(jsonPath("$.length()", is(2)));
	}
	
	@Test
	public void shouldCreateAQuoteToAStock() throws Exception {
		String stockId = "petr4";
		
		JSONObject quotes = new JSONObject();
		quotes.put("2021-01-10", "250");
		quotes.put("2021-01-11", "350");
		JSONObject data = new JSONObject();
		data.put("id", stockId);
		data.put("quotes", quotes);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/quote")
				.content(data.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201))
		.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
		.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));;
	}
	
	@Test
	public void shouldNotCreateAQuoteBecauseStockNotExists() throws Exception {
		String stockId = "petr1";
		
		JSONObject quotes = new JSONObject();
		quotes.put("2021-01-10", "250");
		quotes.put("2021-01-11", "350");
		JSONObject data = new JSONObject();
		data.put("id", stockId);
		data.put("quotes", quotes);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/quote")
				.content(data.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	public void shouldNotCreateAQuoteBecauseDateIsInvalid() throws Exception {
		String stockId = "petr4";
			
		JSONObject quotes = new JSONObject();
		quotes.put("2021-01-", "250");
		quotes.put("2021-01-11", "350");
		JSONObject data = new JSONObject();
		data.put("id", stockId);
		data.put("quotes", quotes);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/quote")
				.content(data.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldNotCreateAQuoteBecauseValueIsInvalid() throws Exception {
		String stockId = "petr4";
		
		JSONObject quotes = new JSONObject();
		quotes.put("2021-01-10", "-250");
		quotes.put("2021-01-11", "350");
		JSONObject data = new JSONObject();
		data.put("id", stockId);
		data.put("quotes", quotes);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/quote")
				.content(data.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(400));
	}
}
