package de.airport.ejb.model;

@javax.persistence.Entity (name="airplane")
public class Airplane {
    @javax.persistence.Id
    @javax.persistence.GeneratedValue
	private int id;
    
	private String name;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
