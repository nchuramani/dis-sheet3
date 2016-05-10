package org.hibernate.sheet3.domain;

import java.sql.Date;

import javax.persistence.*;


@Entity
@Table(name="CONTRACT")
public class Contract {
	@Id
	private String CONTRACT_NO;
	
	private Date CONTRACT_DATE;
	
	private String PLACE;
		
	public Contract() {
		// TODO Auto-generated constructor stub
	}

	public String getCONTRACT_NO() {
		return CONTRACT_NO;
	}

	public void setCONTRACT_NO(String cONTRACT_NO) {
		CONTRACT_NO = cONTRACT_NO;
	}

	public Date getCONTRACT_DATE() {
		return CONTRACT_DATE;
	}

	public void setCONTRACT_DATE(Date cONTRACT_DATE) {
		CONTRACT_DATE = cONTRACT_DATE;
	}

	public String getPLACE() {
		return PLACE;
	}

	public void setPLACE(String pLACE) {
		PLACE = pLACE;
	}

}
