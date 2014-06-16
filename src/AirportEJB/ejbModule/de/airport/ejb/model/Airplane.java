package de.airport.ejb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@javax.persistence.Entity (name="airplane")
public class Airplane implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5850955168988007782L;
	
	//-------- Attributes --------------------------------------------------------------------
    
	@Id
	@Column(name="Airplane_Id", unique=true)
	private String airplaneId;
	
	@ManyToOne
	@JoinColumn(name="airline_id")
	private Airline airline;
	
	@ManyToOne
	@JoinColumn(name="airplane_type")
	private AirplaneType airplaneType;
	
	@ManyToOne
	@JoinColumn(name="flight_controller")
	private FlightController flightController;
	
	@OneToOne
	@JoinColumn(name="runway_id")
	private Runway runway;
	
	@OneToOne
	@JoinColumn(name="parkingPosition_id")
	private ParkingPosition parkingPosition;
	
	public enum State { IN_QUEUE, IN_APPROACH, ACCEPTED, LANDED, PARKING }
	private State state;
	
	private Date estimatedLandingTime;
	
	private Date actualLandingTime;
	
	//-------- Getters and setters --------------------------------------------------------------
	
	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public String getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(String airplane_Id) {
		this.airplaneId = airplane_Id;
	}

	public AirplaneType getAirplaneType() {
		return airplaneType;
	}

	public void setAirplaneType(AirplaneType airplaneType) {
		this.airplaneType = airplaneType;
	}

	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
	}

	public ParkingPosition getParkingPosition() {
		return parkingPosition;
	}

	public void setParkingPosition(ParkingPosition parkingPosition) {
		this.parkingPosition = parkingPosition;
	}

	public FlightController getFlightController() {
		return flightController;
	}

	public void setFlightController(FlightController flightController) {
		this.flightController = flightController;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public Date getEstimatedLandingTime() {
		return estimatedLandingTime;
	}

	public void setEstimatedLandingTime(Date estimatedLandingTime) {
		this.estimatedLandingTime = estimatedLandingTime;
	}
	
	public Date getActualLandingTime() {
		return actualLandingTime;
	}

	public void setActualLandingTime(Date actualLandingTime) {
		this.actualLandingTime = actualLandingTime;
	}
	
}
