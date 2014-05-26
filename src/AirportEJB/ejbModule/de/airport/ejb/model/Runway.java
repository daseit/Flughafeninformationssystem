package de.airport.ejb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@javax.persistence.Entity (name="runway")
public class Runway implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8333183316866584847L;

	@javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="airplane_id")
	private Airplane airplane;
	
	private Date reservationTimeStart;
	private Date reservationTimeEnd;
	
	public int getId() {
		return id;
	}
	
	public boolean isReserved() {
		return airplane != null;
	}
	
	public Airplane getAirplane() {
		return airplane;
	}
	
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
	
	public Date getReservationTimeStart() {
		return reservationTimeStart;
	}
	
	public void setReservationTimeStart(Date reservationTimeStart) {
		this.reservationTimeStart = reservationTimeStart;
	}
	
	public Date getReservationTimeEnd() {
		return reservationTimeEnd;
	}
	
	public void setReservationTimeEnd(Date reservationTimeEnd) {
		this.reservationTimeEnd = reservationTimeEnd;
	}
}
