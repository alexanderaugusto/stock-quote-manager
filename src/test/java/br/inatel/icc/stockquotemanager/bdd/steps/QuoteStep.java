package br.inatel.icc.stockquotemanager.bdd.steps;

import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.inatel.icc.stockquotemanager.bdd.CucumberSpringConfiguration;
import br.inatel.icc.stockquotemanager.dto.StockDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class QuoteStep extends CucumberSpringConfiguration{
	private ResultActions response;
	private JSONObject data;
	
	@Before
	public void before() {
		data = new JSONObject();
	}
	
	@Given("a stock of id {string} quote of date {string} and value {string}")
	public void a_stock_of_id_quote_of_date_and_value(String stockId, String date, String value) throws JSONException {
	    StockDto stock = new StockDto();
		stock.setId(stockId);
		Mockito.when(stockService.findById(stockId)).thenReturn(stock);
		
		JSONObject quotes = new JSONObject();
	    quotes.put(date, value);
	    
	    data.put("id", stockId);
	    data.put("quotes", quotes);
	}

	
	@When("call the api route /quote passing the data")
	public void call_the_api_route_quote_passing_the_data() throws Exception {
		response = mockMvc.perform(MockMvcRequestBuilders
				.post("/quote")
				.content(data.toString())
				.contentType(MediaType.APPLICATION_JSON));
	}
	
	@Then("the quote is created and the response status will be {int}")
	public void the_quote_is_created_and_the_response_status_will_be(Integer status) throws Exception {
	    response.andExpect(MockMvcResultMatchers.status().is(status));
	}
}
