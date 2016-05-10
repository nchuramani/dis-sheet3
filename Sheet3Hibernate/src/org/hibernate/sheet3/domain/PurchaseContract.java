package org.hibernate.sheet3.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="PURCHASE_CONTRACT")
public class PurchaseContract implements Serializable {

	
	private int INSTAL;
	
	private float INTEREST_RATE;
	
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="CONTRACT_NO")
	private Contract contract;
	
	@OneToOne
	@JoinColumn(name="ESTATE_ID")
	private House house;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="FIRST_NAME"),
		@JoinColumn(name="NAME")})
	private Person person;
	
	public int getINSTAL() {
		return INSTAL;
	}


	public void setINSTAL(int iNSTAL) {
		INSTAL = iNSTAL;
	}


	public float getINTEREST_RATE() {
		return INTEREST_RATE;
	}


	public void setINTEREST_RATE(float iNTEREST_RATE) {
		INTEREST_RATE = iNTEREST_RATE;
	}


	public Contract getContract() {
		return contract;
	}


	public void setContract(Contract contract) {
		this.contract = contract;
	}


	public House getHouse() {
		return house;
	}


	public void setHouse(House house) {
		this.house = house;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}
	

}