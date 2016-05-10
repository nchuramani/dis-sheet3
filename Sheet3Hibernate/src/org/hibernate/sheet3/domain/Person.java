package org.hibernate.sheet3.domain;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="PERSON")
@IdClass(PersonPK.class)
public class Person {
	
	@Id
	private String FIRST_NAME;
	
	@Id
	private String NAME;
		
	private String ADDRESS;
	
	@OneToMany
	@JoinColumns({
		@JoinColumn(name="FIRST_NAME"),
		@JoinColumn(name="NAME")})
	private Set <TenancyContract> tcontracts;
	
	@OneToMany
	@JoinColumns({
		@JoinColumn(name="FIRST_NAME"),
		@JoinColumn(name="NAME")})
	private Set <PurchaseContract> pcontracts;
	
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getFIRST_NAME() {
		return this.FIRST_NAME;
	}



	public void setFIRST_NAME(String FIRST_NAME) {
		this.FIRST_NAME = FIRST_NAME;
	}



	public String getNAME() {
		return this.NAME;
	}



	public void setNAME(String NAME) {
		this.NAME = NAME;
	}



	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}

	public Set<TenancyContract> getTcontracts() {
		return tcontracts;
	}

	public void setTcontracts(Set<TenancyContract> tcontracts) {
		this.tcontracts = tcontracts;
	}

	public Set<PurchaseContract> getPcontracts() {
		return pcontracts;
	}

	public void setPcontracts(Set<PurchaseContract> pcontracts) {
		this.pcontracts = pcontracts;
	}

	
}
