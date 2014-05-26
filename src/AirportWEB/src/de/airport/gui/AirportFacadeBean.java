package de.airport.gui;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

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
	
	// add new airline
	private String airlineName;
	private String airlineAddress;

	@EJB
	private AirportFacade facade;

	
	// -------- methods ------------------
	/**
	 * Add a new airplane to the system.
	 * Necessary values are stored in the AirportFacadeBean.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return Empty String for JSF command button.
	 */
	public String addAirplane() {
		facade.addAirplane(this.airplaneType, this.airline, this.flightController);
		
		return "";
	}

	/**
	 * Add a new airline to the system.
	 * Necessary values are stored in the AirportFacadeBean.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String addAirline() {
		facade.addAirline(this.airlineName, this.airlineAddress);
		
		return "";
	}
	
	
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

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineAddress() {
		return airlineAddress;
	}

	public void setAirlineAddress(String airlineAddress) {
		this.airlineAddress = airlineAddress;
	}

	public AirportFacade getFacade() {
		return facade;
	}

	public void setFacade(AirportFacade facade) {
		this.facade = facade;
	}

	
}
