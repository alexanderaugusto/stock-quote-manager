package br.inatel.icc.stockquotemanager.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.inatel.icc.stockquotemanager.StockQuoteManagerApplication;
import br.inatel.icc.stockquotemanager.dto.StockDto;

@Service
public class StockService {

	private String defaultUrl = "http://localhost:8080";
	private RestTemplate restTemplate;
	
	public StockService() {
		restTemplate = new RestTemplate();
	}

	@Cacheable(value = "stock")
	public StockDto findById(String id) {
		StockQuoteManagerApplication.logger.info("Getting stock from external API");
		StockDto stock = restTemplate.getForObject(defaultUrl + "/stock/" + id, StockDto.class);
		return stock;
	}
	
	@Cacheable(value = "stocks")
	public List<StockDto> findAll() {
		StockQuoteManagerApplication.logger.info("Getting all stocks from external API");
		StockDto[] stocks = restTemplate.getForObject(defaultUrl + "/stock", StockDto[].class);
		return Arrays.asList(stocks);
	}
	
	public void registerForNotification() {
		StockQuoteManagerApplication.logger.info("Register for notification in external API");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject data = new JSONObject();
	    data.put("host", "localhost");
	    data.put("port", "8081");
		HttpEntity<String> request = new HttpEntity<String>(data.toString(), headers);
		restTemplate.postForObject(defaultUrl + "/notification", request, String.class);
	}
}
