package org.hibernate.sheet3.domain;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="ESTATE")
public class Estate {
	
	@Id
	private String ESTATE_ID;
	
	private String CITY;
	
	private String POSTAL_CODE;
	
	private String STREET;
	
	private int STREET_NUMBER;
	
	private float SQUARE_AREA;
		
	@ManyToOne
	@JoinColumn(name="AGENT_LOGIN")
	private EstateAgent Agent;
	
	public Estate() {
		// TODO Auto-generated constructor stub
	}

	public String getESTATE_ID() {
		return ESTATE_ID;
	}

	public void setESTATE_ID(String eSTATE_ID) {
		ESTATE_ID = eSTATE_ID;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	public String getPOSTAL_CODE() {
		return POSTAL_CODE;
	}

	public void setPOSTAL_CODE(String pOSTAL_CODE) {
		POSTAL_CODE = pOSTAL_CODE;
	}

	public String getSTREET() {
		return STREET;
	}

	public void setSTREET(String sTREET) {
		STREET = sTREET;
	}

	public int getSTREET_NUMBER() {
		return STREET_NUMBER;
	}

	public void setSTREET_NUMBER(int sTREET_NUMBER) {
		STREET_NUMBER = sTREET_NUMBER;
	}

	public float getSQUARE_AREA() {
		return SQUARE_AREA;
	}

	public void setSQUARE_AREA(float sQUARE_AREA) {
		SQUARE_AREA = sQUARE_AREA;
	}

	public EstateAgent getAgent() {
		return Agent;
	}

	public void setAgent(EstateAgent agent) {
		Agent = agent;
	}
}
