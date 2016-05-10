package org.hibernate.sheet3.domain;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="ESTATE_AGENT")
public class EstateAgent {

	private String NAME;
	
	private String ADDR;
	
	@Id
	@Column(name="LOGIN")
	private String LOGIN;
	
	private String PASSWORD;
	
	@OneToMany
	@JoinColumn(name="AGENT_LOGIN")
	private Set <Estate> estate;
	
	public EstateAgent() {
		// TODO Auto-generated constructor stub
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getADDR() {
		return ADDR;
	}

	public void setADDR(String aDDR) {
		ADDR = aDDR;
	}

	public String getLOGIN() {
		return LOGIN;
	}

	public void setLOGIN(String lOGIN) {
		LOGIN = lOGIN;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public Set<Estate> getEstate() {
		return estate;
	}

	public void setEstate(Set<Estate> estate) {
		this.estate = estate;
	}
	
	

}
