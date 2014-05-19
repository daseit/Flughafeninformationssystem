package de.airport.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.airport.ejb.model.Airplane;

@Stateless
@LocalBean
public class AirportFacade {

	// DB access
	@PersistenceContext(unitName = "airport")
	private EntityManager em;

	
	
	
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