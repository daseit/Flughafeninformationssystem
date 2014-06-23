package de.airport.ejb;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.airport.ejb.model.Account;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;
import de.airport.ejb.model.Airplane.State;
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

	// init system
	public AirportFacade() {
		
		/*
		Timer timer = new Timer();
		
		System.out.println("\n\n\n");
		System.out.println("--------------------------------------------------------------------------------------------------");
		System.out.println("---------- FLUGHAFENINFORMATIONSSYSTEM by Daniel Secker, Stefan Lutz and Benjamin Rupp -----------");
		System.out.println("--------------------------------------------------------------------------------------------------");
		
		System.out.println("\nPrepare system to start..");
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				System.out.println("\nCreate 4 runways..");
				addRunways(4);
				
				System.out.println("\nCreate 10 parking positions..");
				addParkingPositions(10);
				
				System.out.println("\nCreate 2 flight controller..");
				addFlightController("Hans", "Maier", "hansi_maier", "geheim");
				addFlightController("Gudrun", "Schmidt", "gundi", "geheim");
				
				System.out.println("\nCreate 3 airlines");
				addAirline("myAirline", "Echterdingerstr. 18", "Stuttgart", "Germany");
				addAirline("AirStefaan", "Holstenstr. 1", "Hamburg", "Germany");
				addAirline("Daniel Fly", "Eifelturmgasse 4711", "Paris", "France");
				
				System.out.println("\nCreate 2 airplane types");
				addAirplaneType("Boeing 747");
				addAirplaneType("Airbus A380");
				addAirplaneType("Aero Commander 500");
				
			}
			
		}, 2*1000);
		*/
		
	}
	
	
	// airplane
	/**
	 * Add a new airplane to the system. (Requirement 11400)
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @param airplaneTypeName Unique airplane name.
	 * @param airlineName Unique airline name.
	 * @param flightControllerId Unique flight controller identifier.
	 */
	public void addAirplane(String airplaneTypeName, String airlineName, int flightControllerId, String id) {
		
		Airplane airplane = new Airplane();
		airplane.setState(Airplane.State.IN_APPROACH);
		
		// id		
		airplane.setAirplaneId(id);
		
		// get objects by id
		Query qAirplaneType = em.createQuery("select e from airplaneType e where e.name = '" + airplaneTypeName + "'");
		Query qAirline = em.createQuery("select e from airline e where e.name = '" + airlineName + "'");
		Query qFlightController = em.createQuery("select e from flightController e where e.id = '" + flightControllerId + "'");
		
		// airplane type
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
		
		
		// write to database
		em.persist(airplane);
		
		System.out.println("[AirportFacade][addAirplane] Added new airplane " + airplane.getAirplaneId() +
				" to airline " + airplane.getAirline().getName());
		
	}
		
	/**
	 * Return all airplane objects which are not landed yet.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return List with all airplane objects which are not landed yet.
	 */
	@SuppressWarnings("unchecked")
	public List<Airplane> getRegisteredAirplanes() {
		
		int parkingState = Airplane.State.PARKING.ordinal();
		Query query = em.createQuery("select e from airplane e where e.state != '" + parkingState + "' order by e.id");
		return query.getResultList();
		
	}
	
	/**
	 * Return all airplane objects which have the state ACCEPTED or LANDED.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return List with all airplane objects which have the state ACCEPTED or LANDED.
	 */
	@SuppressWarnings("unchecked")
	public List<Airplane> getSelectableAirplanes() {
		
		int acceptedState = Airplane.State.ACCEPTED.ordinal();
		int landedState = Airplane.State.LANDED.ordinal();
		
		Query query = em.createQuery("select e from airplane e where e.state = '" + acceptedState + "' or e.state = '" + landedState + "' order by e.id");
		return query.getResultList();
		
	}
	
	/**
	 * Return the airplane with the given airplane id.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId Unique airplane identifier.
	 * @return The airplane with the given airplane id.
	 */
	public Airplane getAirplane(String airplaneId) {
		
		Airplane temp = null;
		Query query = em.createQuery("select e from airplane e where e.id = '" + airplaneId + "'");
		
		if(query.getResultList().size() > 0) {
			temp = (Airplane) query.getResultList().get(0);
		}
		
		return temp;
		
	}
	
	/**
	 * Return all airplane objects stored in the database.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @return List with all airplane objects stored in the database.
	 */
	@SuppressWarnings("unchecked")
	public List<Airplane> getAirplanes() {
		
		Query query = em.createQuery("select e from airplane e order by e.id");
		return query.getResultList();
		
	}
	
	/**
	 * A method to simulate the 5 different states of an airplane. When called, it set the next status of the airplane.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId Unique airplane identifier.
	 */
	public void nextState(String airplaneId) {
		
		Query qCurrentState = em.createQuery("select e from airplane e where e.id = '" + airplaneId + "'");
		
		if( qCurrentState.getResultList().size() > 0) {
			
			Airplane airplane = (Airplane) qCurrentState.getResultList().get(0);
			Airplane.State newState = State.IN_APPROACH;
			
			// set new state
			switch(airplane.getState()) {
			
			case IN_APPROACH:
				newState = State.ACCEPTED;
				break;
			
			case ACCEPTED:
			case IN_QUEUE:
				newState = State.LANDED;
				break;
			
			case LANDED:
				newState = State.PARKING;
				releaseRunway(getAirplane(airplaneId).getRunway().getId());
				break;
			
			case PARKING:
				newState = State.PARKING;
				break;
			
			}
			
			// write to database
			Query qNewState = em.createQuery("update airplane set state = '" + newState.ordinal() + "'" +
								" where id = '" + airplaneId + "'");
			int updateCnt = qNewState.executeUpdate();
			
			if(updateCnt <= 0) {
				System.out.println("[AirportFacade][nextState] Error: Set new state " +
									newState + " failed! No database update.");
			}
			
			
		}else{
			System.out.println("[AirportFacade][nextState] Error: No airplane found! (airplane id: " + airplaneId + ")");
			return;
		}
		
	}
	
	/**
	 * Set estimated landing time for airplane.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId
	 */
	public void setEstimatedLandingTime(String airplaneId, java.sql.Timestamp estimatedLandingTime) {
		
		// write to database
		Query q = em.createQuery("update airplane set estimatedLandingTime = '" + estimatedLandingTime + "'" +
							" where id = '" + airplaneId + "'");
		int updateCnt = q.executeUpdate();
		
		if(updateCnt <= 0) {
			System.out.println("[AirportFacade][setEstimatedLandingTime] Error: Set estimated landing time " +
								estimatedLandingTime + " failed! No database update.");
		}else{
			System.out.println("[AirportFacade][setEstimatedLandingTime] Set estimated landing time for airplane " + airplaneId + " to " + estimatedLandingTime);
		}
		
	}
	
	/**
	 * Set actual landing time for airplane.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId
	 */
	public void setActualLandingTime(String airplaneId, java.sql.Timestamp actualLandingTime) {
		
		// write to database
		Query q = em.createQuery("update airplane set actualLandingTime = '" + actualLandingTime + "'" +
							" where id = '" + airplaneId + "'");
		int updateCnt = q.executeUpdate();
		
		if(updateCnt <= 0) {
			System.out.println("[AirportFacade][setActualLandingTime] Error: Set actual landing time " +
					actualLandingTime + " failed! No database update.");
		}else{
			System.out.println("[AirportFacade][setActualLandingTime] Set actual landing time for airplane " + airplaneId + " to " + actualLandingTime);
		}
		
	}
	
	/**
	 * Set a airplanes state.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId Unique airplane identifier.
	 * @param state New airplane state.
	 */
	public void setAirplaneState(String airplaneId, Airplane.State state) {

		int stateInt = state.ordinal();
		
		Query qAirplane = em.createQuery("update airplane set state = '" + stateInt + "'" +
				" where id = '" + airplaneId + "'");
		int updateCnt = qAirplane.executeUpdate();
		
		if(updateCnt <= 0) {
			System.out.println("[AirportFacade][setAirplaneState] Error: Set airplane state to " + state + " failed! No database update.");
		}else{
			System.out.println("[AirportFacade][setAirplaneState] Set state to " + state + " for airplane " + airplaneId);
		}		
	}
	
	
	// airline
	/**
	 * Add a new airline to the system. (Requirement 11405)
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @param name Airline name.
	 * @param street Airline street.
	 * @param city Airline city.
	 * @param country Airline country.
	 */
	public void addAirline(String name, String street, String city, String country) {
		
		// create objects
		Account account = new Account();
		Airline airline = new Airline();
		
		// set airline properties
		airline.setName(name);
		airline.setAddress(street + ":" + city + ":" + country);
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
	 * Return all airline objects from the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all airline objects stored in the database.
	 */
	@SuppressWarnings("unchecked")
	public List<Airline> getAirlines() {
		
		Query query = em.createQuery("select e from airline e order by e.name");
		return query.getResultList();
		
	}
	
	/**
	 * Print current airlines to console.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 */
	@SuppressWarnings("unchecked")
	public void printAirlines() {
		
		Query q = em.createQuery("select e from airline e order by e.id");
		
		System.out.println("\nAirlines\n--------------------------------");
		
		for(Airline a : (List<Airline>) q.getResultList()) {
			
			System.out.println("#" + a.getName() + "\t" + a.getAddress());
			
		}
	}
	
	
	// airplane type
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

	
	// runways	
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
	 * Return the runway with the given runway id.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param runwayId Unique runway identifier.
	 * @return The runway with the given runway id.
	 */
	public Runway getRunway(int runwayId) {
		
		Runway temp = null;
		Query query = em.createQuery("select e from runway e where e.id = '" + runwayId + "'");
		
		if(query.getResultList().size() > 0) {
			temp = (Runway) query.getResultList().get(0);
		}
		
		return temp;
		
	}
	
	/**
	 * Return all free runways.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all free runways.
	 */
	@SuppressWarnings("unchecked")
	public List<Runway> getFreeRunways() {
		
		Query query = em.createQuery("select e from runway e where e.airplane = null order by e.id");
		return query.getResultList();
		
	}
		
	/**
	 * Reserve a runway for landing or start of an airplane. (Requirement 11415)
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param runwayId Unique runway identifier.
	 * @param reservationStartTime Reservation start time.
	 * @param airplaneId Unique airplane identifier.
	 */
	public void reserveRunway(int runwayId, Date reservationStartTime, String airplaneId) {
		
		// modify runway object in database
		Query qRunway = em.createQuery("update runway set airplane = '" + airplaneId + "'" +
							", reservationTimeStart = '" + reservationStartTime + "'" +
							" where id = '" + runwayId + "'");
		int updateCnt = qRunway.executeUpdate();
		
		if(updateCnt <= 0) {
			System.out.println("[AirportFacade][reserveRunway] Error: Reserving runway " +
								runwayId + " failed! No database update.");
		}
		
		// modify airplane object in database
		Query qAirplane = em.createQuery("update airplane set runway = '" + runwayId + "'" +
						" where id = '" + airplaneId + "'");
		int updateCnt2 = qAirplane.executeUpdate();
		
		if(updateCnt2 <= 0) {
		System.out.println("[AirportFacade][reserveRunway] Error: Set runway " +
							runwayId + " on airplane " + airplaneId + " failed! No database update.");
		}
		
	}
	
	/**
	 * Release reserved runway.
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param runwayId Unique runway id.
	 */
	public void releaseRunway(int runwayId) {
		
		String airplaneId = getRunway(runwayId).getAirplane().getAirplaneId();
		
		Query qRunway = em.createQuery("update runway set airplane = null, reservationTimeStart = null, " +
									"reservationTimeEnd = null where id = '" + runwayId + "'");
		
		Query qAirplane = em.createQuery("update airplane set runway = null where id = '" + airplaneId + "'");
		
		if(qRunway.executeUpdate() <= 0) {
			System.out.println("[AirportFacade][releaseRunway] Error: Release runway " +
					runwayId + " failed! No database update.");
		}
		if(qAirplane.executeUpdate() <= 0) {
			System.out.println("[AirportFacade][releaseRunway] Error: Remove runway " +
					runwayId + " from airplane " + airplaneId + " failed! No database update.");
		}
		
	}
	
	
	// parking positions
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
	 * Return all free runways.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @return List with all free runways.
	 */
	@SuppressWarnings("unchecked")
	public List<Runway> getFreeParkingPositions() {
		
		Query query = em.createQuery("select e from parking_position e where e.airplane = null order by e.id");
		return query.getResultList();
		
	}
			
	/**
	 * Reserve a parking position for landing. (Requirement 11425)
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param parkingPositionId Unique parking position identifier
	 * @param reservationStartTime Reservation start time.
	 * @param reservationEndTime Reservation end time.
	 * @param airplaneId Unique airplane identifier.
	 */
	public void reserveParkingPosition(int parkingPositionId, Date reservationStartTime, Date reservationEndTime, String airplaneId) {
		
		// modify parking position object in database
		Query qParkingPosition = em.createQuery("update parking_position set airplane = '" + airplaneId + "'" +
							", reservationTimeStart = '" + reservationStartTime + "'" +
							", reservationTimeEnd = '" + reservationEndTime + "'" +
							" where id = '" + parkingPositionId + "'");
		int updateCnt = qParkingPosition.executeUpdate();
		
		if(updateCnt <= 0) {
			System.out.println("[AirportFacade][reserveParkingPosition] Error: Reserving parking postion " +
								parkingPositionId + " failed! No database update.");
		}else{
			System.out.println("[AirportFacade][reserveParkingPosition] Reserve parking postion " +
					parkingPositionId + " for airplane " + airplaneId);
		}
		
	}


	// order queue & cancel landing
	/**
	 * Set airplane state to IN_QUEUE. (Requirement 11430)
	 * @author Benjamin Rupp <beruit01@hs-essingen.de>
	 * @param airplaneId
	 */
	public void orderQueue(String airplaneId) {
		
		setAirplaneState(airplaneId, Airplane.State.IN_QUEUE);
		
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
	public void cancelLanding(String airplaneId) {
		
		// TODO: Release runway and parking position?
		// TODO: Remove airplane from flight controller?
		Query q = em.createQuery("delete from airplane c WHERE c.id = '" + airplaneId + "'");
		
		if(q.executeUpdate() <= 0) {
			System.out.println("[AirportFacade][cancelLanding] Error: Cancel landing for airplane " +
					airplaneId + " failed! No database update.");
		}
		
	}

	
	// init
	/**
	 * Add parking positions to the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @param n Number of parking positions.
	 */
	public void addParkingPositions(int n) {
		
		for(int i=0; i<n; i++) {
			
			em.persist( new ParkingPosition() );
			
		}
	}
	
	/**
	 * Add runways to the database.
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @param Number of runways.
	 */
	public void addRunways(int n) {
		
		for(int i=0; i<n; i++) {
			
			em.persist( new Runway() );
			
		}
	}
	
	
	
	// TODO: Remove this methods. Only for testing!
	@SuppressWarnings("unchecked")
	public void printAirplaneTypes() {
		
		System.out.println("\nAirplane types\n--------------------------------");

		Query q = em.createQuery("select e from airplaneType e order by e.id");
		
		for(AirplaneType at : (List<AirplaneType>) q.getResultList()) {
			
			System.out.println("#" + at.getName());
			
		}
	}
	@SuppressWarnings("unchecked")
	public void printFlightController() {
		
		System.out.println("\nFlight controller\n--------------------------------");

		Query q = em.createQuery("select e from flightController e order by e.id");
		
		for(FlightController f : (List<FlightController>) q.getResultList()) {
			
			System.out.println("#" + f.getId() + "\t" + f.getForname() + " " + f.getSurname());
			
		}
	}
	public String printRunways() {
		
		List<Runway> list = getRunways();
		
		System.out.println("\nRunway status\n--------------------");
		for(Runway r : list) {
			System.out.print("#" + r.getId() + "\t" +
						r.getReservationTimeStart() + "\t" +
						r.getReservationTimeEnd() + "\t");
			
			if(r.getAirplane() == null) System.out.print("-\n");
			else System.out.print(r.getAirplane().getAirplaneId() + "\n");
		}
		
		return "";
	}
	public String printParkingPositions() {
		
		List<ParkingPosition> list = getParkingPositions();
		
		System.out.println("\nParking position status\n--------------------");
		for(ParkingPosition p : list) {
			System.out.print("#" + p.getId() + "\t" +
						p.getReservationTimeStart() + "\t" +
						p.getReservationTimeEnd() + "\t");
			
			if(p.getAirplane() == null) System.out.print("-\n");
			else System.out.print(p.getAirplane().getAirplaneId() + "\n");
		}
		
		return "";
	}
	@SuppressWarnings("unchecked")
	public void printAirplanes() {
		
		Query q = em.createQuery("select e from airplane e order by e.id");
		
		System.out.println("\nAirplanes\n--------------------------------");
		System.out.println("id \tairline \ttype \t\tcontroller \trunway \tparking \tstatus");
		
		for(Airplane a : (List<Airplane>) q.getResultList()) {
			
			System.out.print("#" + a.getAirplaneId() + "\t" + a.getAirline().getName() + "\t" + a.getAirplaneType().getName() + "\t" +
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
}
