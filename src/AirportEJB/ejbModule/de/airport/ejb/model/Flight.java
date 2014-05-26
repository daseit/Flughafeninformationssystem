package de.airport.ejb.model;

import java.io.Serializable;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@javax.persistence.Entity (name="flight")
public class Flight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5767305235804139200L;

	@javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String origin;
	
	private String destination;
	
	@OneToOne
	@JoinColumn(name="airplane_id")
	private Airplane airplane;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public int getId() {
		return id;
	}
	
}
