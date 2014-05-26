package de.airport.ejb.model;

import javax.persistence.GenerationType;

@javax.persistence.Entity (name="airplaneType")
public class AirplaneType {
	
	@javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
