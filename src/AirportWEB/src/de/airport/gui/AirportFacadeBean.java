package de.airport.gui;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.model.*;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	
	// -------- parameters ---------------
	// add new airplane
	private AirplaneType airplaneType;
	private Airline airline;
	private FlightController flightController;

	@EJB
	private AirportFacade facade;

	
	// -------- methods ------------------
	/**
	 * Add a new airplane to the system.
	 * Necessary values are stored in the AirportFacadeBean.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 */
	public void addAirplane() {
		facade.addAirplane(this.airplaneType, this.airline, this.flightController);
	}

	public String getName() {return "name";}
	public void createAirplane() {};
	public List<Airplane> getAirplanes() { return new ArrayList<>(); };
	
	// getters and setters
	public AirplaneType getAirplaneType() {
		return airplaneType;
	}
	public void setAirplaneType(AirplaneType airplaneType) {
		this.airplaneType = airplaneType;
	}

	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public FlightController getFlightController() {
		return flightController;
	}
	public void setFlightController(FlightController flightController) {
		this.flightController = flightController;
	}
}
