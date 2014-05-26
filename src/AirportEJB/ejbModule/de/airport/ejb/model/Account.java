package de.airport.ejb.model;

import java.io.Serializable;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@javax.persistence.Entity (name="account")
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4561190208886549787L;

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String iban;
	
	private String bic;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}
	
	
	
}
