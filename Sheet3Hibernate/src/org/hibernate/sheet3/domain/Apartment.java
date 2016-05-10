package org.hibernate.sheet3.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;



@Entity
@Table(name="APARTMENT")
public class Apartment implements Serializable {

	private int FLOOR;
	
	private double RENT;
	
	private int ROOMS;

	private char BALCONY;
	
	private char KITCHEN;
	
	@Id
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ESTATE_ID", nullable =false)
	private Estate estate;
	
	
	public Apartment() {
		// TODO Auto-generated constructor stub
	}

	public int getFLOOR() {
		return FLOOR;
	}

	public void setFLOOR(int fLOOR) {
		FLOOR = fLOOR;
	}

	public double getRENT() {
		return RENT;
	}

	public void setRENT(double rENT) {
		RENT = rENT;
	}

	public int getROOMS() {
		return ROOMS;
	}

	public void setROOMS(int rOOMS) {
		ROOMS = rOOMS;
	}

	public char getBALCONY() {
		return BALCONY;
	}

	public void setBALCONY(char bALCONY) {
		BALCONY = bALCONY;
	}

	public char getKITCHEN() {
		return KITCHEN;
	}

	public void setKITCHEN(char kITCHEN) {
		KITCHEN = kITCHEN;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}
	
	
}
