package de.airport.ejb.model;

import java.io.Serializable;

import javax.persistence.*;


@javax.persistence.Entity (name="airplane")
public class Airplane implements Serializable {
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.AUTO, generator="my_airplane_seq_gen")
	@SequenceGenerator(name="my_airplane_seq_gen", sequenceName="MY_AIRPLANE_SEQ")
	private int id;
    
	private String name;
	private int airlineId; //id
	private String lotse;
	private int status = 0;
	private String plannedTime;
	private String realTime;
	
	/*@ManyToOne
	@JoinColumn(name="airline_id")
	private Airline airline;*/
	
	/*@OneToOne
	@JoinColumn(name="runway_id")
	private Runway runway;
	
	@OneToOne
	@JoinColumn(name="parkingPosition_id")
	private ParkingPosition parkingPosition;*/
	
	public String getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(String plannedTime) {
		this.plannedTime = plannedTime;
	}

	public String getRealTime() {
		return realTime;
	}

	public void setRealTime(String realTime) {
		this.realTime = realTime;
	}

	public int getAirline() {
		return airlineId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLotse() {
		return lotse;
	}

	public void setLotse(String lotse) {
		this.lotse = lotse;
	}

	public void setAirline(int airline) {
		this.airlineId = airline;
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
