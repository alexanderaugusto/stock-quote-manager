package br.inatel.icc.stockquotemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.icc.stockquotemanager.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long>{

	List<Quote> findByStockId(String stockId);
}
