package de.airport.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.model.Airplane;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	private String name;

	@EJB
	private AirportFacade facade;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void createAirplane() {
		facade.createAirplane(name);
	}
	
	public List<Airplane> getAirplanes() {
		return facade.getAirplanes();
	}
}
