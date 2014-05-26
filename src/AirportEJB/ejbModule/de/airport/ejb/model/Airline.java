package de.airport.ejb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

@javax.persistence.Entity (name="airline")
public class Airline implements Serializable {
	
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String adress;
	
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

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	

}
