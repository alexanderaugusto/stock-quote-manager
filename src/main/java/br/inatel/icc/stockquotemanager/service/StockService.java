package br.inatel.icc.stockquotemanager.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import br.inatel.icc.stockquotemanager.dto.StockDto;

public class StockService {

	private String defaultUrl = "http://localhost:8080";
	private RestTemplate restTemplate;

	public StockService() {
		restTemplate = new RestTemplate();
	}

	public List<StockDto> getAll() {
		StockDto[] stocks = restTemplate.getForObject("http://localhost:8080/stock", StockDto[].class);
		return Arrays.asList(stocks);
	}
	
	public StockDto getById(String id) {
		StockDto stock = restTemplate.getForObject(defaultUrl + "/stock/" + id, StockDto.class);
		return stock;
	}
}
