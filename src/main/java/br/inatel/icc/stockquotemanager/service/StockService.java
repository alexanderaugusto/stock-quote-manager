package br.inatel.icc.stockquotemanager.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.inatel.icc.stockquotemanager.dto.StockDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockService {

	private String defaultUrl;
	private RestTemplate restTemplate;
	
	public StockService(@Value("${stock-manager.url}") String defaultUrl) {
		this.defaultUrl = defaultUrl;
		restTemplate = new RestTemplate();
	}

	@Cacheable(value = "stock")
	public StockDto findById(String id) {
		log.info("getting stock from external API");
		StockDto stock = restTemplate.getForObject(defaultUrl + "/stock/" + id, StockDto.class);
		return stock;
	}
	
	@Cacheable(value = "stocks")
	public List<StockDto> findAll() {
		log.info("getting all stocks from external API");
		StockDto[] stocks = restTemplate.getForObject(defaultUrl + "/stock", StockDto[].class);
		return Arrays.asList(stocks);
	}
	
	public void registerForNotification() {
		log.info("gegistering for notification in external API");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject data = new JSONObject();
	    data.put("host", "localhost");
	    data.put("port", "8081");
		HttpEntity<String> request = new HttpEntity<String>(data.toString(), headers);
		restTemplate.postForObject(defaultUrl + "/notification", request, String.class);
	}
}
