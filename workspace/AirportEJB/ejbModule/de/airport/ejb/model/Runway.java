package de.airport.ejb.model;

import java.util.Date;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@javax.persistence.Entity (name="runway")
public class Runway {
	
	@javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.AUTO, generator="my_runway_seq_gen")
	@SequenceGenerator(name="my_runway_seq_gen", sequenceName="MY_RUNWAY_SEQ")
	private int id;
	
//	@OneToOne
//	@JoinColumn(name="airplane_id")
	private int reservedAirplaneId = -1;
	
//	private Date reservationTimeStart;
//	private Date reservationTimeEnd;
	
//	public Runway(int id){
//		this.id = id;
//		airplane = null;
//	}
	
	public int getId() {
		return id;
	}

	public int getReservedAirplaneId() {
		return reservedAirplaneId;
	}

	public void setReservedAirplaneId(int reservedAirplaneId) {
		this.reservedAirplaneId = reservedAirplaneId;
	}
	
//	public void setId(int id) {
//		this.id = id;
//	}
//	
//	public boolean isReserved() {
//		return airplane != null;
//	}
//	
//	public Airplane getAirplane() {
//		return airplane;
//	}
//	
//	public void setAirplane(Airplane airplane) {
//		this.airplane = airplane;
//	}
	
//	public Date getReservationTimeStart() {
//		return reservationTimeStart;
//	}
//	
//	public void setReservationTimeStart(Date reservationTimeStart) {
//		this.reservationTimeStart = reservationTimeStart;
//	}
//	
//	public Date getReservationTimeEnd() {
//		return reservationTimeEnd;
//	}
//	
//	public void setReservationTimeEnd(Date reservationTimeEnd) {
//		this.reservationTimeEnd = reservationTimeEnd;
//	}
}
