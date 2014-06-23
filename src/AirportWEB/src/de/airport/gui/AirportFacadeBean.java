package de.airport.gui;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.model.*;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	
	// -------- parameters ---------------
	// register new airplane
	private String airplaneId;
	private String airplaneTypeName;
	private String addAirplaneAirlineName;
	private int flightControllerId=1;
	private String estimatedLandingDate;
	private String estimatedLandingTime;
	private int addAirplaneRunwayId;
	
	// accept airplane
	private String acceptAirplaneAirplaneId;
	private int acceptAirplaneRunwayId;
	private int acceptAirplaneParkingPositionId;
	private String acceptAirplaneParkingPositionStartDate;
	private String acceptAirplaneParkingPositionStartTime;
	private String acceptAirplaneParkingPositionEndDate;
	private String acceptAirplaneParkingPositionEndTime;
	
	// add new airline
	private String airlineName;
	private String airlineStreet;
	private String airlineCity;
	private String airlineCountry;
	
	// add new airplane type
	private String addAirplaneTypeName;
	
	// reserve runway
	private int runwayId;
	private String runwayAirplaneId;
	private String runwayReservationStartDate;
	private String runwayReservationStartTime;
	
	// reserve parking position
	private int parkingPositionId;
	private String parkingPositionAirplaneId;
	private String parkingPositionReservationStartDate;
	private String parkingPositionReservationStartTime;
	private String parkingPositionReservationEndDate;
	private String parkingPositionReservationEndTime;
	
	// order queue
	private String orderQueueAirplaneId;
	
	// cancel landing
	private String cancelLandingAirplaneId;
	
	// release runway
	private int releaseRunwayId;
	
	// set next state
	private String nextStateAirplaneId;

	@EJB
	private AirportFacade facade;

	
	// -------- methods ------------------
	/**
	 * Register a new airplane.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return Empty String for JSF command button.
	 */
	public String registerAirplane() {
		
		// check id
		List<Airplane> currentAirplanes = facade.getAirplanes();
		
		for(Iterator<Airplane> i = currentAirplanes.iterator(); i.hasNext(); ) {
			
			Airplane a = i.next();
			if(a.getAirplaneId().equals(this.airplaneId)) {
				
				// id already exists
				System.out.println("[AirportFacade][addAirplane] Error: Airplane id " + this.airplaneId + " already exists! Exit.");
				return "";
			}
		}
		
		
		// add airplane to system
		facade.addAirplane(this.airplaneTypeName, this.addAirplaneAirlineName, this.flightControllerId, this.airplaneId);
		
		// reserve runway	
		java.sql.Timestamp estTimestamp = parseStringToSQLTimestamp(this.estimatedLandingDate + " " + this.estimatedLandingTime);
		facade.reserveRunway(this.addAirplaneRunwayId, estTimestamp, this.airplaneId);
		
		// add estimated date and time
		facade.setEstimatedLandingTime(this.airplaneId, estTimestamp);
		
		return "";
	}
	
	/**
	 * Accept a airplane and reserve parking position.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return
	 */
	public String acceptAirplane() {
		
		// reserve parking position		
		java.sql.Timestamp sqlDateStart = parseStringToSQLTimestamp(this.acceptAirplaneParkingPositionStartDate + " " + this.acceptAirplaneParkingPositionStartTime);
		java.sql.Timestamp sqlDateEnd = parseStringToSQLTimestamp(this.acceptAirplaneParkingPositionEndDate + " " + this.acceptAirplaneParkingPositionEndTime);
		
		facade.reserveParkingPosition(this.acceptAirplaneParkingPositionId, sqlDateStart, sqlDateEnd, this.acceptAirplaneAirplaneId);
		
		// set airplane state to ACCEPTED
		facade.setAirplaneState(this.acceptAirplaneAirplaneId, Airplane.State.ACCEPTED);
		
		// set actual landing time
		java.sql.Timestamp currentSqlTimestamp = new java.sql.Timestamp(new Date().getTime());
		facade.setActualLandingTime(this.acceptAirplaneAirplaneId, currentSqlTimestamp);
		
		// reserve runway
		Runway prevRunway = facade.getAirplane(this.acceptAirplaneAirplaneId).getRunway();
		
		if(prevRunway != null) {			
			facade.releaseRunway(prevRunway.getId());
		}
		facade.reserveRunway(this.acceptAirplaneRunwayId, currentSqlTimestamp, this.acceptAirplaneAirplaneId);
		
		return "";
	}
	
	/**
	 * A method to simulate the 5 different states of an airplane. When called, it set the next status of the airplane.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId Unique airplane identifier.
	 * @return Empty string for JSF command button.
	 */
	public String nextState() {
		
		facade.nextState(this.nextStateAirplaneId);
		return "";
	}
	
	/**
	 * Return all airplane objects which are not landed yet.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return List with all airplane objects which are not landed yet.
	 */
	public List<Airplane> getRegisteredAirplanes() {
		return facade.getRegisteredAirplanes();
	}
	
	/**
	 * Return all airplane objects which have the state ACCEPTED or LANDED.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return List with all airplane objects which have the state ACCEPTED or LANDED.
	 */
	public List<Airplane> getSelectableAirplanes() {
		return facade.getSelectableAirplanes();
	}
	
	/**
	 * Add a new airline to the system.
	 * Necessary values are stored in the AirportFacadeBean.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String addAirline() {
		facade.addAirline(this.airlineName, this.airlineStreet, this.airlineCity, this.airlineCountry);
		
		return "";
	}
	
	/**
	 * Return all airline objects from the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all airline objects stored in the database.
	 */
	public List<Airline> getAirlines() {
		
		return facade.getAirlines();
		
	}
	
	/**
	 * Return all runways objects from the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all runway objects stored in the database.
	 */
	public List<Runway> getRunways() {
		
		return facade.getRunways();
		
	}
	
	/**
	 * Return all free runways.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all free runways.
	 */
	public List<Runway> getFreeRunways() {
		return facade.getFreeRunways();
	}

	/**
	 * Reserve a runway for landing or start of an airplane.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String reserveRunway() {
		
		// parse date			
		java.sql.Timestamp sqlDate = parseStringToSQLTimestamp(this.runwayReservationStartDate + " " + this.runwayReservationStartTime);
		
		// delegate
		facade.reserveRunway(this.runwayId, sqlDate, this.runwayAirplaneId);
		
		return "";
	}
	
	/**
	 * Return all parking position objects from the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all parking position objects stored in the database.
	 */
	public List<ParkingPosition> getParkingPositions() {
		
		return facade.getParkingPositions();
		
	}
	
	/**
	 * Return all free parking positions.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all free parking positions.
	 */
	public List<Runway> getFreeParkingPositions() {
		return facade.getFreeParkingPositions();
	}
	
	/**
	 * Set airplane state to IN_QUEUE.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String orderQueue() {
		facade.orderQueue(this.acceptAirplaneAirplaneId);
		
		return "";
	}
	
	/**
	 * Return a list of all airplanes in state IN_QUEUE.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return List with all airplanes in state IN_QUEUE.
	 */
	public List<Airplane> getQueue() {
		return facade.getQueue();
	}
	
	/**
	 * Add new airplane type to the system.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String addAirplaneType() {
		facade.addAirplaneType(this.addAirplaneTypeName);
		return "";
	}
	
	/**
	 * Return all airplane type objects from the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all airplane type objects stored in the database.
	 */
	public List<AirplaneType> getAirplaneTypes() {
		return facade.getAirplaneTypes();
	}
	
	/**
	 * Remove airplane from system (database).
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String cancelLanding() {
		
		facade.cancelLanding(this.cancelLandingAirplaneId);
		
		return "";
	}
	
	/**
	 * Release reserved runway.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String releaseRunway() {
		
		facade.releaseRunway(this.releaseRunwayId);
		
		return "";
	}
	
	// private stuff
	/**
	 * Parse given String with format dd.MM.yyyy HH:mm to SQL timestamp.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param str Date string with format dd.MM.yyyy HH:mm
	 * @return SQL date of date string.
	 */
	private java.sql.Timestamp parseStringToSQLTimestamp(String str) {
		
		Date date;
		java.sql.Timestamp sqlDate = null;
		try {
			
			date = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN).parse(str);
			sqlDate = new java.sql.Timestamp(date.getTime());
			
		} catch (ParseException e) {
			System.out.println("[AirportFacadeBean][parseStringToDate] Can not parse the following string to a SQL date: " + str);
			e.printStackTrace();
		}
		
		return sqlDate;
	}
	
	
	// TESTING ONLY AREA!
	// TODO: Remove these methods!
	public String init() {
		
		System.out.println("\nInit demo page..\n\n");
		
		System.out.println("\nCreate flight controller..");
		facade.addFlightController("Hans", "Maier", "hansi_maier", "geheim");
		facade.addFlightController("Gudrun", "Schmidt", "gundi", "geheim");
		facade.printFlightController();
		
		System.out.println("\nCreate runways..");
		facade.addRunways(4);
		facade.printRunways();
		
		System.out.println("\nCreate parking positions..");
		facade.addParkingPositions(10);
		facade.printParkingPositions();
		
		System.out.println("\nCreate airlines");
		facade.addAirline("myAirline", "Echterdingerstr. 18", "Stuttgart", "Germany");
		facade.addAirline("AirStefaan", "Holstenstr. 1", "Hamburg", "Germany");
		facade.addAirline("Daniel Fly", "Eifelturmgasse 4711", "Paris", "France");
		facade.printAirlines();
		
		System.out.println("\nCreate airplane types");
		facade.addAirplaneType("Boeing 747");
		facade.printAirplaneTypes();
		
		System.out.println("\nCreate airplanes");
		facade.addAirplane("Boeing 747", "myAirline", 1, "HS1234");
		facade.addAirplane("Boeing 747", "AirStefaan", 2, "HS4711");
		return "";
	}
	public String printInfo() {
		
		System.out.println("\n\nINFO:\n------------------");
		facade.printAirlines();
		facade.printAirplanes();
		facade.printAirplaneTypes();
		facade.printFlightController();
		facade.printRunways();
		facade.printParkingPositions();
		
		return "";
	}
	public String printAirplanes() {
		facade.printAirplanes();
		return "";
	}
	
	// getters and setters
	public String getAirlineName() {
		return airlineName;
	}
	public String getAirplaneTypeName() {
		return airplaneTypeName;
	}
	public void setAirplaneTypeName(String airplaneTypeName) {
		this.airplaneTypeName = airplaneTypeName;
	}
	public String getAddAirplaneAirlineName() {
		return addAirplaneAirlineName;
	}
	public void setAddAirplaneAirlineName(String airlineName) {
		this.addAirplaneAirlineName = airlineName;
	}
	public int getFlightControllerId() {
		return flightControllerId;
	}
	public void setFlightControllerId(int flightControllerId) {
		this.flightControllerId = flightControllerId;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}	
	public String getAirlineStreet() {
		return airlineStreet;
	}
	public void setAirlineStreet(String airlineStreet) {
		this.airlineStreet = airlineStreet;
	}
	public String getAirlineCity() {
		return airlineCity;
	}
	public void setAirlineCity(String airlineCity) {
		this.airlineCity = airlineCity;
	}
	public String getAirlineCountry() {
		return airlineCountry;
	}
	public void setAirlineCountry(String airlineCountry) {
		this.airlineCountry = airlineCountry;
	}
	public AirportFacade getFacade() {
		return facade;
	}
	public void setFacade(AirportFacade facade) {
		this.facade = facade;
	}
	public int getRunwayId() {
		return runwayId;
	}
	public void setRunwayId(int runwayId) {
		this.runwayId = runwayId;
	}
	public String getRunwayAirplaneId() {
		return runwayAirplaneId;
	}
	public void setRunwayAirplaneId(String runwayAirplaneId) {
		this.runwayAirplaneId = runwayAirplaneId;
	}
	public String getRunwayReservationStartDate() {
		return runwayReservationStartDate;
	}
	public void setRunwayReservationStartDate(String runwayReservationStartDate) {
		this.runwayReservationStartDate = runwayReservationStartDate;
	}
	public String getAirplaneId() {
		return airplaneId;
	}
	public void setAirplaneId(String airplaneId) {
		this.airplaneId = airplaneId;
	}
	public int getParkingPositionId() {
		return parkingPositionId;
	}
	public void setParkingPositionId(int parkingPositionId) {
		this.parkingPositionId = parkingPositionId;
	}
	public String getParkingPositionAirplaneId() {
		return parkingPositionAirplaneId;
	}
	public void setParkingPositionAirplaneId(String parkingPositionAirplaneId) {
		this.parkingPositionAirplaneId = parkingPositionAirplaneId;
	}
	public String getParkingPositionReservationStartDate() {
		return parkingPositionReservationStartDate;
	}
	public void setParkingPositionReservationStartDate(
			String parkingPositionReservationStartDate) {
		this.parkingPositionReservationStartDate = parkingPositionReservationStartDate;
	}
	public String getOrderQueueAirplaneId() {
		return orderQueueAirplaneId;
	}
	public void setOrderQueueAirplaneId(String orderQueueAirplaneId) {
		this.orderQueueAirplaneId = orderQueueAirplaneId;
	}
	public String getCancelLandingAirplaneId() {
		return cancelLandingAirplaneId;
	}
	public void setCancelLandingAirplaneId(String cancelLandingAirplaneId) {
		this.cancelLandingAirplaneId = cancelLandingAirplaneId;
	}
	public int getReleaseRunwayId() {
		return releaseRunwayId;
	}
	public void setReleaseRunwayId(int releaseRunwayId) {
		this.releaseRunwayId = releaseRunwayId;
	}
	public String getRunwayReservationStartTime() {
		return runwayReservationStartTime;
	}
	public void setRunwayReservationStartTime(String runwayReservationStartTime) {
		this.runwayReservationStartTime = runwayReservationStartTime;
	}
	public String getParkingPositionReservationStartTime() {
		return parkingPositionReservationStartTime;
	}
	public void setParkingPositionReservationStartTime(
			String parkingPositionReservationStartTime) {
		this.parkingPositionReservationStartTime = parkingPositionReservationStartTime;
	}
	public String getParkingPositionReservationEndDate() {
		return parkingPositionReservationEndDate;
	}
	public void setParkingPositionReservationEndDate(
			String parkingPositionReservationEndDate) {
		this.parkingPositionReservationEndDate = parkingPositionReservationEndDate;
	}
	public String getParkingPositionReservationEndTime() {
		return parkingPositionReservationEndTime;
	}
	public void setParkingPositionReservationEndTime(
			String parkingPositionReservationEndTime) {
		this.parkingPositionReservationEndTime = parkingPositionReservationEndTime;
	}
	public String getAddAirplaneTypeName() {
		return addAirplaneTypeName;
	}
	public void setAddAirplaneTypeName(String addAirplaneTypeName) {
		this.addAirplaneTypeName = addAirplaneTypeName;
	}
	public String getNextStateAirplaneId() {
		return nextStateAirplaneId;
	}
	public void setNextStateAirplaneId(String nextStateAirplaneId) {
		this.nextStateAirplaneId = nextStateAirplaneId;
	}
	public String getEstimatedLandingTime() {
		return estimatedLandingTime;
	}
	public void setEstimatedLandingTime(String estimatedLandingTime) {
		this.estimatedLandingTime = estimatedLandingTime;
	}
	public String getEstimatedLandingDate() {
		return estimatedLandingDate;
	}
	public void setEstimatedLandingDate(String estimatedLandingDate) {
		this.estimatedLandingDate = estimatedLandingDate;
	}
	public int getAddAirplaneRunwayId() {
		return addAirplaneRunwayId;
	}
	public void setAddAirplaneRunwayId(int addAirplaneRunwayId) {
		this.addAirplaneRunwayId = addAirplaneRunwayId;
	}
	public String getAcceptAirplaneAirplaneId() {
		return acceptAirplaneAirplaneId;
	}
	public void setAcceptAirplaneAirplaneId(String acceptAirplaneAirplaneId) {
		this.acceptAirplaneAirplaneId = acceptAirplaneAirplaneId;
	}
	public int getAcceptAirplaneRunwayId() {
		return acceptAirplaneRunwayId;
	}
	public void setAcceptAirplaneRunwayId(int acceptAirplaneRunwayId) {
		this.acceptAirplaneRunwayId = acceptAirplaneRunwayId;
	}
	public int getAcceptAirplaneParkingPositionId() {
		return acceptAirplaneParkingPositionId;
	}
	public void setAcceptAirplaneParkingPositionId(
			int acceptAirplaneParkingPositionId) {
		this.acceptAirplaneParkingPositionId = acceptAirplaneParkingPositionId;
	}
	public String getAcceptAirplaneParkingPositionStartDate() {
		return acceptAirplaneParkingPositionStartDate;
	}
	public void setAcceptAirplaneParkingPositionStartDate(
			String acceptAirplaneParkingPositionStartDate) {
		this.acceptAirplaneParkingPositionStartDate = acceptAirplaneParkingPositionStartDate;
	}
	public String getAcceptAirplaneParkingPositionStartTime() {
		return acceptAirplaneParkingPositionStartTime;
	}
	public void setAcceptAirplaneParkingPositionStartTime(
			String acceptAirplaneParkingPositionStartTime) {
		this.acceptAirplaneParkingPositionStartTime = acceptAirplaneParkingPositionStartTime;
	}
	public String getAcceptAirplaneParkingPositionEndDate() {
		return acceptAirplaneParkingPositionEndDate;
	}
	public void setAcceptAirplaneParkingPositionEndDate(
			String acceptAirplaneParkingPositionEndDate) {
		this.acceptAirplaneParkingPositionEndDate = acceptAirplaneParkingPositionEndDate;
	}
	public String getAcceptAirplaneParkingPositionEndTime() {
		return acceptAirplaneParkingPositionEndTime;
	}
	public void setAcceptAirplaneParkingPositionEndTime(
			String acceptAirplaneParkingPositionEndTime) {
		this.acceptAirplaneParkingPositionEndTime = acceptAirplaneParkingPositionEndTime;
	}
}
