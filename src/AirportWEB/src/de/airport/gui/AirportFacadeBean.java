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
	// add new airplane
	private int airplaneTypeId;
	private int airlineId;
	private int flightControllerId;
	private String airplaneName;
	
	// add new airline
	private String airlineName;
	private String airlineAddress;
	
	// reserve runway
	private int runwayId;
	private int runwayAirplaneId;
	private String runwayReservationStartDate;
	
	// reserve parking position
	private int parkingPositionId;
	private int parkingPositionAirplaneId;
	private String parkingPositionReservationStartDate;
	
	// order queue
	private int orderQueueAirplaneId;

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
		
		facade.addAirplane(this.airplaneTypeId, this.airlineId, this.flightControllerId, this.airplaneName);
		
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
			// TODO: find better solution for date input
			date = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.GERMAN).parse(this.runwayReservationStartDate);
		
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		
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
	 * Reserve a parking position for landing of an airplane.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String reserveParkingPosition() {
		
		Date date = null;
		try {
			// TODO: find better solution for date input
			date = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.GERMAN).parse(this.runwayReservationStartDate);
		
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		
		facade.reserveParkingPosition(this.parkingPositionId, sqlDate, this.parkingPositionAirplaneId);
		
		return "";
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
	public int getAirplaneTypeId() {
		return airplaneTypeId;
	}
	public void setAirplaneTypeId(int airplaneTypeId) {
		this.airplaneTypeId = airplaneTypeId;
	}
	public int getAirlineId() {
		return airlineId;
	}
	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
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
	public String getAirplaneName() {
		return airplaneName;
	}
	public void setAirplaneName(String airplaneName) {
		this.airplaneName = airplaneName;
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
	
}
