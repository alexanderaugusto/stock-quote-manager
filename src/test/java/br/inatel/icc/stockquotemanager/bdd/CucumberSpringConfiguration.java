package br.inatel.icc.stockquotemanager.bdd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.inatel.icc.stockquotemanager.StockQuoteManagerApplication;
import br.inatel.icc.stockquotemanager.service.StockService;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = StockQuoteManagerApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CucumberSpringConfiguration {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	@MockBean
	protected StockService stockService;
}
