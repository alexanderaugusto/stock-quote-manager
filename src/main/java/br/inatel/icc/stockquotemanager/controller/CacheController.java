package br.inatel.icc.stockquotemanager.controller;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.icc.stockquotemanager.service.StockService;

@RestController
@RequestMapping("/stockcache")
public class CacheController {

	private Logger logger = LogManager.getLogger(StockService.class);
	
	@Autowired
	public CacheController(StockService stockService) {
		stockService.registerForNotification();
	}

	@DeleteMapping
	@Transactional
	@Caching(evict = { @CacheEvict("stocks"), @CacheEvict(value = "stock") })
	public ResponseEntity<?> cleanCache() {
		logger.info("Cleaning cache");
		return ResponseEntity.status(204).build();
	}
}
