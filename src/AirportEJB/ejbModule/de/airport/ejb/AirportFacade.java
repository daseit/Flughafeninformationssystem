package de.airport.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;
import de.airport.ejb.model.AirplaneType;
import de.airport.ejb.model.FlightController;

@Stateless
@LocalBean
public class AirportFacade {

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
		
	}
	
	
	public void createAirplane(String name) {
		Airplane airplane = new Airplane();
		airplane.setName(name);
		em.persist(airplane);
	}

	public List<Airplane> getAirplanes() {
		Query query = em.createQuery("select e from airplane e order by e.name");
		return query.getResultList();
	}
	
}
