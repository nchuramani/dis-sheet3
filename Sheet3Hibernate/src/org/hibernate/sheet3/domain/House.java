package org.hibernate.sheet3.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="HOUSE")
public class House implements Serializable {

	private int FLOORS;
	
	private double PRICE;
	
	private char GARDEN;
	
	@Id
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ESTATE_ID")
	private Estate estate;
	
	public House() {
		// TODO Auto-generated constructor stub
	}

	public int getFLOORS() {
		return FLOORS;
	}

	public void setFLOORS(int fLOORS) {
		FLOORS = fLOORS;
	}

	public double getPRICE() {
		return PRICE;
	}

	public void setPRICE(double pRICE) {
		PRICE = pRICE;
	}

	public char getGARDEN() {
		return GARDEN;
	}

	public void setGARDEN(char gARDEN) {
		GARDEN = gARDEN;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}
	

}