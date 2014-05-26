package de.airport.ejb.model;

import java.io.Serializable;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@javax.persistence.Entity (name="airline")
public class Account implements Serializable {
	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="airline_id")
	private Airline airline;

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public int getId() {
		return id;
	}
	
}
