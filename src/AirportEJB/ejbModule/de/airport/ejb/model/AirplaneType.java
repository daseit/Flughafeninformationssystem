package de.airport.ejb.model;

import java.io.Serializable;

@javax.persistence.Entity (name="airplaneType")
public class AirplaneType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9167763938804602381L;
	
    @javax.persistence.Id
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
