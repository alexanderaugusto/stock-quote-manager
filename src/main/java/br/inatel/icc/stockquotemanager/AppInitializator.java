package br.inatel.icc.stockquotemanager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inatel.icc.stockquotemanager.service.StockService;

@Component
public class AppInitializator {

	@Autowired
	private StockService stockService;
	
	@PostConstruct
	private void init() {
		stockService.registerForNotification();
	}
}
