package de.airport.ejb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

@javax.persistence.Entity (name="flightController")
public class FlightController {

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String forname;
	
	private String surname;
	
	private String loginname;
	
	private String password;
	
	@OneToMany(mappedBy="flightController")
	private List<Airplane> airplanes = new ArrayList<Airplane>();

	public String getForname() {
		return forname;
	}

	public void setForname(String forname) {
		this.forname = forname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public List<Airplane> getAirplanes() {
		return airplanes;
	}

	public void setAirplanes(List<Airplane> airplanes) {
		this.airplanes = airplanes;
	}
	
}
