package br.inatel.icc.stockquotemanager.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.icc.stockquotemanager.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long>{

	List<Quote> findByStockId(String stockId);
	Optional<Quote> findByStockIdAndDate(String stockId, LocalDate date);
}
