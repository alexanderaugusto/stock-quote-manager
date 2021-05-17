package br.inatel.icc.stockmanager.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Quote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Stock stock;
	
	private LocalDateTime date;
	private Double value;
	
	public Quote() {
	}
	
	public Quote(LocalDateTime date, Double value) {
		this.date = date;
		this.value = value;
	}
	
	public Long getId() {
		return id;
	}
	
	public Stock getStock() {
		return stock;
	}

	public LocalDateTime getDate() {
		return date;
	}
	
	public Double getValue() {
		return value;
	}
}
