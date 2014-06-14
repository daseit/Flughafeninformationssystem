package de.airport.ejb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@javax.persistence.Entity (name="airline")
public class Airline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2101775176751972529L;

	@Id
	private String name;
	
	private String address;
	
	@OneToOne
	@JoinColumn(name="account_id")
	private Account account;
	
	@OneToMany(mappedBy="airline")
	private List<Airplane> airplanes = new ArrayList<Airplane>();
	
	public List<Airplane> getAirplanes() {
		return airplanes;
	}

	public void setAirplanes(List<Airplane> airplanes) {
		this.airplanes = airplanes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	

}
