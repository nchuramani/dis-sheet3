package org.hibernate.sheet3.domain;

import java.io.Serializable;


public class PersonPK implements Serializable{
	/**
	 * 
	 */
	protected String FIRST_NAME;
	protected String NAME;
	

	public PersonPK() {
		// TODO Auto-generated constructor stub
	}
	public PersonPK(String FIRST_NAME, String NAME) {
		
		this.setFIRST_NAME(FIRST_NAME);
		this.setNAME(NAME);
	}
	public String getFIRST_NAME() {
		return FIRST_NAME;
	}
	public void setFIRST_NAME(String fIRST_NAME) {
		this.FIRST_NAME = fIRST_NAME;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

}
