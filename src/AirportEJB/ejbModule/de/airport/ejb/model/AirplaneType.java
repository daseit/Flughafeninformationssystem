package de.airport.ejb.model;

import java.io.Serializable;

import javax.persistence.GenerationType;

@javax.persistence.Entity (name="airplaneType")
public class AirplaneType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9167763938804602381L;

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
