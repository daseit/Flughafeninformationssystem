package de.airport.ejb;

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

@Stateless
@LocalBean
public class AirportFacade {

	private List<Airline> airlines;
	
	// DB access
	@PersistenceContext(unitName = "airport")
	private EntityManager em;

	/**
	 * Add a new airplane to the system. (Requirement 11400)
	 * @author Benjamin Rupp <beruit01@hs-esslingen.de>
	 * @param type AirplaneType of the new airplane.
	 * @param airline Airline of the new airplane.
	 * @param controller Flight controller of the new airplane.
	 */
	public void addAirplane(AirplaneType type, Airline airline, FlightController controller) {
		
		Airplane airplane = new Airplane();
		
		airplane.setAirline(airline);
		airplane.setAirplaneType(type);
		airplane.setFlightController(controller);
		
		em.persist(airplane);
		
		System.out.println("[AirportFacade] Added new airplane of type " + airplane.getAirplaneType() +
				" to airline " + airplane.getAirline().getName());
		
	}
	
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
	
	
	/*
	public void createAirplane(String name) {
		Airplane airplane = new Airplane();
		airplane.setName(name);
		em.persist(airplane);
	}

	public List<Airplane> getAirplanes() {
		Query query = em.createQuery("select e from airplane e order by e.name");
		return query.getResultList();
	}
	*/
}
