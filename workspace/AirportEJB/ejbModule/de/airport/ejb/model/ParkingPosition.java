package de.airport.ejb.model;

import java.util.Date;

import javax.persistence.*;

@javax.persistence.Entity (name="parkingPosition")
public class ParkingPosition {
	
	@javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy=GenerationType.AUTO, generator="my_parkingPosition_seq_gen")
	@SequenceGenerator(name="my_parkingPosition_seq_gen", sequenceName="MY_ParkingPosition_SEQ")
	private int id;
	
	private int reservedAirplaneId = -1; // -1 => Parking Position is free
	
//	@OneToOne
//	@JoinColumn(name="airplane_id")
//	private Airplane airplane;
	
//	private Date reservationTimeStart;
//	private Date reservationTimeEnd;
	
//	public ParkingPosition(int id){
//		this.id = id;
//		airplane = null;
//	}
	
	public int getReservedAirplaneId() {
		return reservedAirplaneId;
	}

	public void setReservedAirplaneId(int reservedAirplaneId) {
		this.reservedAirplaneId = reservedAirplaneId;
	}

	public int getId() {
		return id;
	}
		
//	public boolean isReserved() {
//		return airplane != null;
//	}
//	
//	public int getAirplane() {
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
