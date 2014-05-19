package de.airport.ejb.model;

import java.io.Serializable;

import javax.persistence.*;


@javax.persistence.Entity (name="airplane")
public class Airplane implements Serializable {
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)//, generator="my_airplane_seq_gen")
//	@SequenceGenerator(name="my_airplane_seq_gen", sequenceName="MY_AIRPLANE_SEQ")
	private int id;
    
	private String name;
	
	@ManyToOne
	@JoinColumn(name="airline_id")
	private Airline airline;
	
	@OneToOne
	@JoinColumn(name="runway_id")
	private Runway runway;
	
	@OneToOne
	@JoinColumn(name="parkingPosition_id")
	private ParkingPosition parkingPosition;
	
	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
