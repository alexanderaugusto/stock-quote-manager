package br.inatel.icc.stockquotemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.icc.stockquotemanager.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long>{

}
