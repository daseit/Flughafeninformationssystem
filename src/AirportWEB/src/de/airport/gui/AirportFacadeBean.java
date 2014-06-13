package de.airport.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	private String airplaneTypeName;
	private String addAirplaneAirlineName;
	private int flightControllerId;
	private int airplaneId;
	
	// add new airline
	private String airlineName;
	private String airlineAddress;
	
	// add new airplane type
	private String addAirplaneTypeName;
	
	// reserve runway
	private int runwayId;
	private int runwayAirplaneId;
	private String runwayReservationStartDate;
	private String runwayReservationStartTime;
	
	// reserve parking position
	private int parkingPositionId;
	private int parkingPositionAirplaneId;
	private String parkingPositionReservationStartDate;
	private String parkingPositionReservationStartTime;
	
	// order queue
	private int orderQueueAirplaneId;
	
	// cancel landing
	private int cancelLandingAirplaneId;
	
	// release runway
	private int releaseRunwayId;

	@EJB
	private AirportFacade facade;

	
	// -------- methods ------------------
	/**
	 * Register a new airplane.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return Empty String for JSF command button.
	 */
	public String registerAirplane() {
		
		// add airplane to system
		facade.addAirplane(this.airplaneTypeName, this.addAirplaneAirlineName, this.flightControllerId, this.airplaneId);
		
		// reserve runway
		Date date = null;
		try {
			// parse date
			date = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN).parse(this.runwayReservationStartDate + " " + this.runwayReservationStartTime);			
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			
			// delegate
			facade.reserveRunway(this.runwayId, sqlDate, this.airplaneId);
		
		} catch (ParseException e) {
			System.out.println("[AirportFacadeBean][registerAirplane] Cannot parse date " + this.runwayReservationStartDate + " " + this.runwayReservationStartTime);
		}
		
		// add estimated date and time
		// TODO: Needs implemented actual and estimated landing time. (issue #3)
		
		return "";
	}
	
	/**
	 * Accept a airplane and reserve parking position.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return
	 */
	public String acceptAirplane() {
		
		// reserve parking position
		Date date = null;
		try {
			// parse date
			date = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN).parse(this.parkingPositionReservationStartDate + " " + this.parkingPositionReservationStartTime);			
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			
			// delegate
			facade.reserveParkingPosition(this.parkingPositionId, sqlDate, this.parkingPositionAirplaneId);
		
		} catch (ParseException e) {
			System.out.println("[AirportFacadeBean][acceptAirplane] Cannot parse date " + this.parkingPositionReservationStartDate + " " + this.parkingPositionReservationStartTime);
		}
		
		return "";
	}
	

	// TODO: remove this! only for testing.
	public String printAirplanes() {
		facade.printAirplanes();
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
	
	/**
	 * Add 4 runways to database.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String addRunways() {
		facade.addRunways();
		return "";
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
	 * Reserve a runway for landing or start of an airplane.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String reserveRunway() {
		
		Date date = null;
		try {
			// parse date
			date = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN).parse(this.runwayReservationStartDate + " " + this.runwayReservationStartTime);			
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			
			// delegate
			facade.reserveRunway(this.runwayId, sqlDate, this.runwayAirplaneId);
		
		} catch (ParseException e) {
			System.out.println("[AirportFacadeBean][reserveRunway] Cannot parse date " + this.runwayReservationStartDate + " " + this.runwayReservationStartTime);
		}
		
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
	 * Set airplane state to IN_QUEUE.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String orderQueue() {
		facade.orderQueue(this.orderQueueAirplaneId);
		
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
	
	
	// TESTING ONLY AREA!
	// TODO: Remove these methods!
	/**
	 * Init demo site.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String init() {
		
		System.out.println("\nInit demo page..\n\n");
		
		System.out.println("\nCreate flight controller..");
		facade.addFlightController("Hans", "Maier", "hansi_maier", "geheim");
		facade.addFlightController("Gudrun", "Schmidt", "gundi", "geheim");
		facade.printFlightController();
		
		System.out.println("\nCreate runways..");
		facade.addRunways();
		facade.printRunways();
		
		System.out.println("\nCreate parking positions..");
		facade.addParkingPositions();
		facade.printParkingPositions();
		
		System.out.println("\nCreate airlines");
		facade.addAirline("myAirline", "Stuttgart");
		facade.addAirline("AirStefaan", "Hamburg");
		facade.addAirline("Daniel Fly", "Paris");
		facade.printAirlines();
		
		System.out.println("\nCreate airplane types");
		facade.addAirplaneType("Boeing 747");
		facade.printAirplaneTypes();
		
		return "";
	}
	/**
	 * Print info.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
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
	public int getRunwayId() {
		return runwayId;
	}
	public void setRunwayId(int runwayId) {
		this.runwayId = runwayId;
	}
	public int getRunwayAirplaneId() {
		return runwayAirplaneId;
	}
	public void setRunwayAirplaneId(int runwayAirplaneId) {
		this.runwayAirplaneId = runwayAirplaneId;
	}
	public String getRunwayReservationStartDate() {
		return runwayReservationStartDate;
	}
	public void setRunwayReservationStartDate(String runwayReservationStartDate) {
		this.runwayReservationStartDate = runwayReservationStartDate;
	}
	public int getAirplaneId() {
		return airplaneId;
	}
	public void setAirplaneId(int airplaneId) {
		this.airplaneId = airplaneId;
	}
	public int getParkingPositionId() {
		return parkingPositionId;
	}
	public void setParkingPositionId(int parkingPositionId) {
		this.parkingPositionId = parkingPositionId;
	}
	public int getParkingPositionAirplaneId() {
		return parkingPositionAirplaneId;
	}
	public void setParkingPositionAirplaneId(int parkingPositionAirplaneId) {
		this.parkingPositionAirplaneId = parkingPositionAirplaneId;
	}
	public String getParkingPositionReservationStartDate() {
		return parkingPositionReservationStartDate;
	}
	public void setParkingPositionReservationStartDate(
			String parkingPositionReservationStartDate) {
		this.parkingPositionReservationStartDate = parkingPositionReservationStartDate;
	}
	public int getOrderQueueAirplaneId() {
		return orderQueueAirplaneId;
	}
	public void setOrderQueueAirplaneId(int orderQueueAirplaneId) {
		this.orderQueueAirplaneId = orderQueueAirplaneId;
	}
	public int getCancelLandingAirplaneId() {
		return cancelLandingAirplaneId;
	}
	public void setCancelLandingAirplaneId(int cancelLandingAirplaneId) {
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
	public String getAddAirplaneTypeName() {
		return addAirplaneTypeName;
	}
	public void setAddAirplaneTypeName(String addAirplaneTypeName) {
		this.addAirplaneTypeName = addAirplaneTypeName;
	}
	
}
