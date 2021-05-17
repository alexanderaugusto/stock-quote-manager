package br.inatel.icc.stockquotemanager.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Quote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String stockId;
	private LocalDateTime date;
	private BigDecimal value;
	
	
	public Quote() {
	}
	
	public Quote(LocalDateTime date, BigDecimal value, String stockId) {
		this.stockId = stockId;
		this.date = date;
		this.value = value;
	}
	
	public Long getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public BigDecimal getValue() {
		return value;
	}
}