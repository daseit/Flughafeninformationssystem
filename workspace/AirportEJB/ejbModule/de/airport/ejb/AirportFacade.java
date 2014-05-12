package de.airport.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;
import de.airport.ejb.model.ParkingPosition;
import de.airport.ejb.model.Runway;

@Stateless
@LocalBean
public class AirportFacade {

	@PersistenceContext(unitName = "airport")
	private EntityManager em;

	public void createAirplane(String name, int airline, String lotse, String plannedTime) {
		Airplane airplane = new Airplane();
		airplane.setName(name);
		airplane.setAirline(airline);
		airplane.setLotse(lotse);
		airplane.setPlannedTime(plannedTime);
		em.persist(airplane);
	}

	public List<Airplane> getQueue() {
		Query query = em.createQuery("select e from airplane e "
				+ "where e.status = 0 order by e.id");
		return query.getResultList();
	}
	
	public List<Airplane> getActiveLandings() {
		Query query = em.createQuery("select e from airplane e "
				+ "where e.status > 0 order by e.id");
		return query.getResultList();
	}
	
	public void createAirline(String name)
	{
		Airline airline = new Airline();
		airline.setName(name);
		em.persist(airline);
	}
	
	public List<Airline> getAirlines()
	{
		Query query = em.createQuery("select e from airline e order by e.id");
		return query.getResultList();
	}
	
	public String getAirline(int id)
	{
		Query query = em.createQuery("select e.name from airline e where e.id = '" + id+"'");
		return query.getSingleResult().toString();
	}
	
	public List<Runway> getRunways(boolean onlyFree)
	{
		Query query;
		if (onlyFree)
			query = em.createQuery("select e from runway e "
					+ "where e.reservedAirplaneId = -1 order by e.id");
		else 
			query = em.createQuery("select e from runway e order by e.id");
		
		return query.getResultList();
	}
	
	public boolean freeRunway(int airplaneId)
	{
		Query query = em.createQuery("Update e.reservedAirplaneId "
				+ "set e.reservedAirplaneId = -1 "
				+ "from runway e where e.reservedAirplaneId = '" + airplaneId+"'");
		int i = query.executeUpdate();
		if(i==1)
			return true;
		return false;
	}
	
	public boolean reserveRunway(int airplaneId, int runwayId)
	{
		Query query = em.createQuery("Update e.reservedAirplaneId "
				+ "set e.reservedAirplaneId = "+airplaneId+" "
				+ "from runway e where e.id = '" + runwayId+"'");
		int i = query.executeUpdate();
		if(i==1)
			return true;
		return false;
	}
	
	public boolean freeParkingPosition(int airplaneId)
	{
		Query query = em.createQuery("Update e.reservedAirplaneId "
				+ "set e.reservedAirplaneId = -1 "
				+ "from parkingPosition e where e.reservedAirplaneId = '" + airplaneId+"'");
		int i = query.executeUpdate();
		if(i==1)
			return true;
		return false;
	}
	
	public boolean reserveParkingPosition(int airplaneId, int parkingPositionId)
	{
		Query query = em.createQuery("Update e.reservedAirplaneId "
				+ "set e.reservedAirplaneId = "+airplaneId+" "
				+ "from parkingPosition e where e.id = "+parkingPositionId);
		int i = query.executeUpdate();
		if(i==1)
			return true;
		return false;
	}
	
	
	public List<ParkingPosition> getParkingPositions(boolean onlyFree)
	{
		Query query;
		if (onlyFree)
			query = em.createQuery("select e from parkingPosition e "
					+ "where e.reservedAirplaneId = -1 order by e.id");
		else 
			query = em.createQuery("select e from parkingPosition e order by e.id");

		return query.getResultList();
	}
	
	public void createRunway()
	{
		Runway runway = new Runway();
		em.persist(runway);
	}
	
	public void createParkingPosition()
	{
		ParkingPosition parkingPosition = new ParkingPosition();
		em.persist(parkingPosition);
	}
}
