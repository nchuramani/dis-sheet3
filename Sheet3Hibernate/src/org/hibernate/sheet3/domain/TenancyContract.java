package org.hibernate.sheet3.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="TENANCY_CONTRACT")
public class TenancyContract implements Serializable {
	
	private Date START_DATE;
	
	private int DURATION;
	
	private double ADD_COST;
	
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="CONTRACT_NO")
	private Contract contract;
	
	@OneToOne
	@JoinColumn(name="ESTATE_ID", nullable = true, insertable=false, updatable=false)
	private Apartment apartment;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="FIRST_NAME",nullable = false, insertable=false, updatable=false),
		@JoinColumn(name="NAME",nullable = false, insertable=false, updatable=false)})
	private Person person;
	
	public TenancyContract() {
		// TODO Auto-generated constructor stub
	}

	public Date getSTART_DATE() {
		return START_DATE;
	}

	public void setSTART_DATE(Date sTART_DATE) {
		START_DATE = sTART_DATE;
	}

	public int getDURATION() {
		return DURATION;
	}

	public void setDURATION(int dURATION) {
		DURATION = dURATION;
	}

	public double getADD_COST() {
		return ADD_COST;
	}

	public void setADD_COST(double aDD_COST) {
		ADD_COST = aDD_COST;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}


}
