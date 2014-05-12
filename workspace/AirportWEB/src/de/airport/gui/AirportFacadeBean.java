package de.airport.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;
import de.airport.ejb.model.ParkingPosition;
import de.airport.ejb.model.Runway;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	// Login-Variablen
	private String lotse;
	private boolean loggedIn = false;
	// Landung anmelden-Variablen
	private String registerType;
	private int registerAirline;
	private String registerPlannedTime;
	// Fluggesellschaft erstellen-Variablen
	private String airlineName;
	// Landung einleiten-Variablen
	private int acceptAirplane;
	private int acceptRunway;
	private int acceptParkingPosition;


	public String getRegisterPlannedTime() {
		return registerPlannedTime;
	}

	public void setRegisterPlannedTime(String registerPlannedTime) {
		this.registerPlannedTime = registerPlannedTime;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLotse() {
		return lotse;
	}

	public void setLotse(String lotse) {
		this.lotse = lotse;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public int getRegisterAirline() {
		return registerAirline;
	}

	public void setRegisterAirline(int registerAirline) {
		this.registerAirline = registerAirline;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public int getAcceptAirplane() {
		return acceptAirplane;
	}

	public void setAcceptAirplane(int acceptAirplane) {
		this.acceptAirplane = acceptAirplane;
	}

	public int getAcceptRunway() {
		return acceptRunway;
	}

	public void setAcceptRunway(int acceptRunway) {
		this.acceptRunway = acceptRunway;
	}

	

	public int getAcceptParkingPosition() {
		return acceptParkingPosition;
	}

	public void setAcceptParkingPosition(int acceptParkingPosition) {
		this.acceptParkingPosition = acceptParkingPosition;
	}



	@EJB
	private AirportFacade facadeAirport;
	/*@EJB
	private AirlineFacade facadeAirline;
	@EJB
	private RunwayFacade facadeRunway;
	@EJB
	private ParkingspaceFacade facadeParkingspace;*/

	public void createAirplane() {
		facadeAirport.createAirplane(registerType, registerAirline, lotse, registerPlannedTime);
	}
	
	public void createAirline(){
		facadeAirport.createAirline(airlineName);
	}

	public List<Airplane> getQueue() {
		return facadeAirport.getQueue();
	}
	
	public List<Airplane> getActiveLandings() {
		return facadeAirport.getActiveLandings();
	}
	
	public List<Airline> getAirlines()
	{
		return facadeAirport.getAirlines();
	}
	
	private String getAirline(int id)
	{
		return facadeAirport.getAirline(id);
	}
	
	public List<ParkingPosition> getParkingPositionsFree()
	{
		return facadeAirport.getParkingPositions(true);
	}
	
	public List<ParkingPosition> getParkingPositions()
	{
		return facadeAirport.getParkingPositions(false);
	}
	
	
	public List<Runway> getRunwayFree()
	{
		return facadeAirport.getRunways(true);
	}
	
	public List<Runway> getRunway()
	{
		return facadeAirport.getRunways(false);
	}
	
	private void initRunway(int n)
	{
		for (int i=0; i<n;i++)
			facadeAirport.createRunway();
	}
	
	private void initParkingPosition(int n)
	{
		for (int i=0; i<n;i++)
			facadeAirport.createParkingPosition();
	}
	
	public String doLogin() {
		if (getRunway().isEmpty())
			initRunway(5);
		
		if (getParkingPositions().isEmpty())
			initParkingPosition(10);
		
		if (this.lotse != null) {
			this.loggedIn = true;
		}
		return "index";
	}
	
	public String doLogout() {
		this.loggedIn = false;
		return "index";
	}
	
	public String acceptLanding() {
		// HIER KOMMT DURCHREICHE AN EJB-Logik
		return "index";
	}
}
