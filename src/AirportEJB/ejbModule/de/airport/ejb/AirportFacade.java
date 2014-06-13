package de.airport.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.airport.ejb.model.Account;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;
import de.airport.ejb.model.AirplaneType;
import de.airport.ejb.model.FlightController;
import de.airport.ejb.model.ParkingPosition;
import de.airport.ejb.model.Runway;

@Stateless
@LocalBean
public class AirportFacade {
	
	// DB access
	@PersistenceContext(unitName = "airport")
	private EntityManager em;

	// airplane
	/**
	 * Add a new airplane to the system. (Requirement 11400)
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @param airplaneTypeName Unique airplane name.
	 * @param airlineName Unique airline name.
	 * @param flightControllerId Unique flight controller identifier.
	 */
	public void addAirplane(String airplaneTypeName, String airlineName, int flightControllerId, int id) {
		
		Airplane airplane = new Airplane();
		airplane.setState(Airplane.State.IN_APPROACH);
		
		
		// get objects by id
		Query qAirplaneType = em.createQuery("select e from airplaneType e where e.name = '" + airplaneTypeName + "'");
		Query qAirline = em.createQuery("select e from airline e where e.name = '" + airlineName + "'");
		Query qFlightController = em.createQuery("select e from flightController e where e.id = '" + flightControllerId + "'");
		
		// airplane
		if( qAirplaneType.getResultList().size() > 0) {
			AirplaneType airplaneType = (AirplaneType) qAirplaneType.getResultList().get(0);
			airplane.setAirplaneType(airplaneType);
		}else{
			System.out.println("[AirportFacade][addAirplane] Error: No airplane type found! (airplane type name: " + airplaneTypeName + ")");
			return;
		}
		
		// airline
		if( qAirline.getResultList().size() > 0) {
			Airline airline = (Airline) qAirline.getResultList().get(0);
			airplane.setAirline(airline);
		}else{
			System.out.println("[AirportFacade][addAirplane] Error: No airline found! (airline name: " + airlineName + ")");
			return;
		}
		
		// flight controller
		if( qFlightController.getResultList().size() > 0) {			
			FlightController flightController = (FlightController) qFlightController.getResultList().get(0);
			airplane.setFlightController(flightController);
		}else{
			System.out.println("[AirportFacade][addAirplane] Error: No flight controller found! (flight controller id: " + flightControllerId + ")");
			return;
		}
		
		// id
		// TODO: The Airplane class should have id as primary key without auto increment. (issue #13)
		//airplane.setId(id);
		
		
		// write to database
		em.persist(airplane);
		
		System.out.println("[AirportFacade][addAirplane] Added new airplane " + airplane.getName() +
				" to airline " + airplane.getAirline().getName());
		
	}
	
	/**
	 * Print current airplanes to console.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 */
	public void printAirplanes() {
		
		Query q = em.createQuery("select e from airplane e order by e.id");
		
		System.out.println("\nAirplanes\n--------------------------------");
		System.out.println("id \tname \tairline \ttype \t\tcontroller \trunway \tparking \tstatus");
		
		for(Airplane a : (List<Airplane>) q.getResultList()) {
			
			System.out.print("#" + a.getId() + "\t" + a.getName() + "\t" + a.getAirline().getName() + "\t" + a.getAirplaneType().getName() + "\t" +
								a.getFlightController().getId());
			
			if( a.getRunway() == null ) {
				System.out.print("\t-");
			}else{
				System.out.print("\t" + a.getRunway().getId());
			}
			
			if( a.getParkingPosition() == null ) {
				System.out.print("\t-");
			}else{
				System.out.print("\t" + a.getParkingPosition().getId());
			}
				
			System.out.println("\t\t" + a.getState() + "\n");
			
		}
	}
	
	// airline
	/**
	 * Add a new airline to the system. (Requirement 11405)
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @param name Airline name.
	 * @param address Airline address.
	 */
	public void addAirline(String name, String address) {
		
		// create objects
		Account account = new Account();
		Airline airline = new Airline();
		
		// set airline properties
		airline.setName(name);
		airline.setAddress(address);
		airline.setAccount(account);
		
		// write account to db (must be done before writing the airline to the db)
		account.setAirline(airline);
		em.persist(account);
		
		// write airline to db
		em.persist(airline);
		
		System.out.println("[AirportFacade] Added new airline " + airline.getName() +
				" with the following address: " + airline.getAddress());
	
	}
	
	/**
	 * Print current airlines to console.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 */
	public void printAirlines() {
		
		Query q = em.createQuery("select e from airline e order by e.id");
		
		System.out.println("\nAirlines\n--------------------------------");
		
		for(Airline a : (List<Airline>) q.getResultList()) {
			
			System.out.println("#" + a.getId() + "\t" + a.getName() + "\t" + a.getAddress());
			
		}
	}
	
	// airplain type
	/**
	 * Add a new airplane type to the system.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param name Name of airplane type.
	 */
	public void addAirplaneType(String name) {
		
		if(name.isEmpty()) {
			return;
		}
		
		AirplaneType airplaneType = new AirplaneType();
		
		airplaneType.setName(name);
		
		em.persist(airplaneType);
		
	}
	/**
	 * Return all airplane type objects from the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all airplane type objects stored in the database.
	 */
	@SuppressWarnings("unchecked")
	public List<AirplaneType> getAirplaneTypes() {
		
		Query query = em.createQuery("select e from airplaneType e order by e.id");
		return query.getResultList();
		
	}
	
	/**
	 * Print current airplane types to console.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 */
	// TODO: Remove this. Only for testing!
	public void printAirplaneTypes() {
		
		System.out.println("\nAirplane types\n--------------------------------");

		Query q = em.createQuery("select e from airplaneType e order by e.id");
		
		for(AirplaneType at : (List<AirplaneType>) q.getResultList()) {
			
			System.out.println("#" + at.getId() + "\t" + at.getName());
			
		}
	}
	
	// flight controller
	/**
	 * Add new flight controller to the system.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param firstname Firstname.
	 * @param lastname Lastname.
	 * @param login User name for login.
	 * @param password Password for login.
	 */
	public void addFlightController(String firstname, String lastname, String login, String password) {
		
		FlightController flightController = new FlightController();
		
		flightController.setForname(firstname);
		flightController.setSurname(lastname);
		flightController.setLoginname(login);
		flightController.setPassword(password);
		
		em.persist(flightController);
	}
	
	/**
	 * Print current flight controller to console.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 */
	public void printFlightController() {
		
		System.out.println("\nFlight controller\n--------------------------------");

		Query q = em.createQuery("select e from flightController e order by e.id");
		
		for(FlightController f : (List<FlightController>) q.getResultList()) {
			
			System.out.println("#" + f.getId() + "\t" + f.getForname() + " " + f.getSurname());
			
		}
	}
	
	// runways
	/**
	 * Add 4 runways to the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 */
	public void addRunways() {
		
		for(int i=0; i<4; i++) {
			
			em.persist( new Runway() );
			
		}
	}
	
	/**
	 * Return all runways objects from the database. (Requirement 11410)
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all runway objects stored in the database.
	 */
	@SuppressWarnings("unchecked")
	public List<Runway> getRunways() {
		
		Query query = em.createQuery("select e from runway e order by e.id");
		return query.getResultList();
		
	}
	
	/**
	 * Print current runway status to console.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String printRunways() {
		
		List<Runway> list = getRunways();
		
		System.out.println("\nRunway status\n--------------------");
		for(Runway r : list) {
			System.out.print("#" + r.getId() + "\t" +
						r.getReservationTimeStart() + "\t" +
						r.getReservationTimeEnd() + "\t");
			
			if(r.getAirplane() == null) System.out.print("-\n");
			else System.out.print(r.getAirplane().getName() + "\n");
		}
		
		return "";
	}
	
	/**
	 * Reserve a runway for landing or start of an airplane. (Requirement 11415)
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param runwayId Unique runway identifier.
	 * @param reservationStartTime Reservation start time.
	 * @param airplaneId Unique airplane identifier.
	 */
	public void reserveRunway(int runwayId, Date reservationStartTime, int airplaneId) {
		
		// modify runway object in database
		Query qRunway = em.createQuery("update runway set airplane = '" + airplaneId + "'" +
							", reservationTimeStart = '" + reservationStartTime + "'" +
							" where id = '" + runwayId + "'");
		int updateCnt = qRunway.executeUpdate();
		
		if(updateCnt <= 0) {
			System.out.println("[AirportFacade][reserveRunway] Error: Reserving runway " +
								runwayId + " failed! No database update.");
		}
		
	}

	
	// parking positions
	/**
	 * Add 10 parking positions to the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 */
	public void addParkingPositions() {
		
		for(int i=0; i<10; i++) {
			
			em.persist( new ParkingPosition() );
			
		}
	}
	
	/**
	 * Return all parking position objects from the database. (Requirement 11420)
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all parking position objects stored in the database.
	 */
	@SuppressWarnings("unchecked")
	public List<ParkingPosition> getParkingPositions() {
		
		Query query = em.createQuery("select e from parking_position e order by e.id");
		return query.getResultList();
		
	}
	
	/**
	 * Print current parking position status to console.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return Empty string for JSF command button.
	 */
	public String printParkingPositions() {
		
		List<ParkingPosition> list = getParkingPositions();
		
		System.out.println("\nParking position status\n--------------------");
		for(ParkingPosition p : list) {
			System.out.print("#" + p.getId() + "\t" +
						p.getReservationTimeStart() + "\t" +
						p.getReservationTimeEnd() + "\t");
			
			if(p.getAirplane() == null) System.out.print("-\n");
			else System.out.print(p.getAirplane().getName() + "\n");
		}
		
		return "";
	}
	
		
	/**
	 * Reserve a parking position for landing. (Requirement 11425)
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param parkingPositionId Unique parking position identifier
	 * @param reservationStartTime Reservation start time.
	 * @param airplaneId Unique airplane identifier.
	 */
	public void reserveParkingPosition(int parkingPositionId, Date reservationStartTime, int airplaneId) {
		
		// modify parking position object in database
		Query qParkingPosition = em.createQuery("update parking_position set airplane = '" + airplaneId + "'" +
							", reservationTimeStart = '" + reservationStartTime + "'" +
							" where id = '" + parkingPositionId + "'");
		int updateCnt = qParkingPosition.executeUpdate();
		
		if(updateCnt <= 0) {
			System.out.println("[AirportFacade][reserveParkingPosition] Error: Reserving parking postion " +
								parkingPositionId + " failed! No database update.");
		}
		
	}


	/**
	 * Set airplane state to IN_QUEUE. (Requirement 11430)
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId
	 */
	public void orderQueue(int airplaneId) {
		
		int inQueueInt = Airplane.State.IN_QUEUE.ordinal();
		
		Query q = em.createQuery("update airplane set state = '" + inQueueInt + "'" +
							" where id = '" + airplaneId + "'");
		
		int updateCnt = q.executeUpdate();
		
		if(updateCnt <= 0) {
			System.out.println("[AirportFacade][orderQueue] Error: Order queue for airplane " +
								airplaneId + " failed! No database update.");
		}
		
	}
	
	/**
	 * Return a list of all airplanes in state IN_QUEUE.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return List with all airplanes in state IN_QUEUE.
	 */
	@SuppressWarnings("unchecked")
	public List<Airplane> getQueue() {
		
		int inQueueInt = Airplane.State.IN_QUEUE.ordinal();
		Query query = em.createQuery("select e from airplane e where e.state = '" + inQueueInt + "' order by e.id");
		return query.getResultList();
		
	}

	/**
	 * Remove airplane from system (database). (Requirement 11435)
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId
	 */
	public void cancelLanding(int airplaneId) {
		
		// TODO: Release runway and parking position?
		// TODO: Remove airplane from flight controller?
		Query q = em.createQuery("delete from airplane c WHERE c.id = '" + airplaneId + "'");
		
		if(q.executeUpdate() <= 0) {
			System.out.println("[AirportFacade][cancelLanding] Error: Cancel landing for airplane " +
					airplaneId + " failed! No database update.");
		}
		
	}
	
	/**
	 * Release reserved runway.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param runwayId Unique runway id.
	 */
	public void releaseRunway(int runwayId) {
		
		Query q = em.createQuery("update runway set airplane = null, reservationTimeStart = null, " +
									"reservationTimeEnd = null where id = '" + runwayId + "'");
		
		if(q.executeUpdate() <= 0) {
			System.out.println("[AirportFacade][releaseRunway] Error: Release runway " +
					runwayId + " failed! No database update.");
		}
		
	}
}
