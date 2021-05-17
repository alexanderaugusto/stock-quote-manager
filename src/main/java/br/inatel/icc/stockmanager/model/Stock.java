package br.inatel.icc.stockmanager.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Stock {
	
	@Id
	private String id;
	
	@OneToMany(mappedBy = "stock", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Quote> quotes = new HashSet<>();

	public Stock(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public Set<Quote> getQuotes() {
		return quotes;
	}
}
