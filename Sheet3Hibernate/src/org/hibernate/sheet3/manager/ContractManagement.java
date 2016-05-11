package org.hibernate.sheet3.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.sheet3.Main;
import org.hibernate.sheet3.domain.*;
import org.hibernate.sheet3.util.HibernateUtil;

public class ContractManagement {
	
	private static String loggedinUser;
	private char contract_type;
	private Contract contract;
	private Estate estate;
	private House house;
	private Apartment apartment;
	private Person person;
	private PurchaseContract pc;
	private TenancyContract tc;
	public ContractManagement(String loggedin) {
		// TODO Auto-generated constructor stub
		ContractManagement.loggedinUser = loggedin;
		this.contract = new Contract();
		this.estate = new Estate();
		this.apartment = new Apartment();
		this.house = new House();
		this.person = new Person();
		this.pc = new PurchaseContract();
		this.tc = new TenancyContract();
		
	}
	public void contractMenu(){
		 
	    char choice = '0';
		System.out.println("-------------------------------------------------");
		System.out.println("------------CONTRACT MANAGEMENT --------------");
		System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Press 1 for Inserting Persons");
		System.out.println("Press 2 for Creating Contracts");
		System.out.println("Press 3 for Viewing of Contracts");
		System.out.println("Press 4 to go back to Main Menu");
		try{
			 choice = (char) new BufferedReader(new InputStreamReader(System.in)).read();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (choice == '1'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------INSERT PERSONS MODULE--------------");
			System.out.println("-------------------------------------------------");
			
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
		   
			try{
				System.out.println("Enter PERSON FIRST NAME:");
				person.setFIRST_NAME(br.readLine());
				System.out.println("Enter PERSON LAST NAME:");
				person.setNAME(br.readLine());	
				System.out.println("Enter PERSON ADDRESS:");
				person.setADDRESS(br.readLine());

			}
			catch(IOException io){
				io.printStackTrace();
			}
			readPerson(session);
		}
		else if (choice == '2'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------CREATE CONTRACTS MODULE--------------");
			System.out.println("-------------------------------------------------");
			System.out.println("Press P for a new Purchase Contract!");
			System.out.println("Press T for a new Purchase Contract!");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
		   
			try{
				char conty = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				this.setContract_type(conty);
				
				System.out.println("Enter Place:");
				contract.setPLACE(br.readLine());
				if(this.getContract_type()=='P'){
					System.out.println("Enter PERSON LAST NAME:");
					person.setNAME(br.readLine());
					System.out.println("Enter PERSON FIRST NAME:");
					person.setFIRST_NAME(br.readLine());				
					System.out.println("Enter ESTATE ID:");
					String estateidd = br.readLine();
					Query query = (Query) session.createQuery("From Estate where ESTATE_ID =?");
					query.setParameter(0, estateidd);
					List <Estate> estates = query.list();
					if(estates.isEmpty()){
						System.out.println("Estate ID does not exist! Try again!");
						contractMenu();
					}
					else{
						for(Estate es : estates){
							this.estate = es;
						}
						estateidd = this.estate.getESTATE_ID();
						query = (Query) session.createQuery("From PurchaseContract where ESTATE_ID =?");
						query.setParameter(0, estateidd);
						List <PurchaseContract> pestate = query.list();
						if(pestate.isEmpty()){
							query = (Query) session.createQuery("From TenancyContract where ESTATE_ID =?");
							query.setParameter(0, estateidd);
							List <TenancyContract> testate = query.list();
							if(!testate.isEmpty()){
								System.out.println("A Tenancy Contract already exists for "+estate.getESTATE_ID()+"! Try again with a different Estate ID");
								contractMenu();
							}
						}
						else{
							System.out.println("A Purchase Contract already exists for "+estate.getESTATE_ID()+"! Try again with a different Estate ID");
							contractMenu();
						}
					}
					house.setEstate(this.estate);
					System.out.println("Enter Number of Installments:");
					pc.setINSTAL(Integer.parseInt(br.readLine()));
					System.out.println("Enter Interest Rate:");
					pc.setINTEREST_RATE(Float.parseFloat(br.readLine()));
					readContract(session);
					
				}
				else if(this.getContract_type()=='T'){
					System.out.println("Enter Start Date (YYYY-MM-DD):");
					
					DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
					java.util.Date date1 = formatter.parse(br.readLine());
					tc.setSTART_DATE(new java.sql.Date(date1.getTime()));
					System.out.println("Enter Duration:");
					tc.setDURATION(Integer.parseInt(br.readLine()));				
					System.out.println("Enter Additional Cost:");
					tc.setADD_COST(Double.parseDouble(br.readLine()));
					System.out.println("Enter PERSON LAST NAME:");
					person.setNAME(br.readLine());
					System.out.println("Enter PERSON FIRST NAME:");
					person.setFIRST_NAME(br.readLine());	
					System.out.println("Enter ESTATE ID:");
					String estateidd = br.readLine();
					Query query = (Query) session.createQuery("From Estate where ESTATE_ID =?");
					query.setParameter(0, estateidd);
					List <Estate> estates = query.list();
					if(estates.isEmpty()){
						System.out.println("Estate ID does not exist! Try again!");
						contractMenu();
					}
					else{
						for(Estate es : estates){
							this.estate = es;
						}
						query = (Query) session.createQuery("From TenancyContract where ESTATE_ID =?");
						query.setParameter(0, estateidd);
						List <TenancyContract> testate = query.list();
						if(testate.isEmpty()){
							query = (Query) session.createQuery("From PurchaseContract where ESTATE_ID =?");
							query.setParameter(0,estateidd);
							List <PurchaseContract> pestate = query.list();
							if(!pestate.isEmpty()){
								System.out.println("A Purchase Contract already exists for "+estate.getESTATE_ID()+"! Try again with a different Estate ID");
								contractMenu();
							}
						}
						else{
							System.out.println("A Tenancy Contract already exists for "+estate.getESTATE_ID()+"! Try again with a different Estate ID");
							contractMenu();
						}
					}
					apartment.setEstate(this.estate);
					readContract(session);
				}
				else{
					System.out.println("Invalid Contract Type! Try again!");
					contractMenu();
				}
				contractMenu();
			}
			catch(IOException io){
				io.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else if (choice == '3'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------VIEW CONTRACTS MODULE--------------");
			System.out.println("-------------------------------------------------");
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
		   
			viewPurchaseContracts(session);
			contractMenu();
			//viewSaleContracts();
			
		}
		else if(choice=='4'){
			Main.menuDisplay();
		}
		else{
			System.out.println("Invalid choice! Choose again!");
			contractMenu();
		}
	}
	
	
	public void createPerson(Session session){
		session.save(person);
		
		System.out.println("Person" + person.getFIRST_NAME() +  " "+person.getNAME() +"added.");
		session.getTransaction().commit();
		contractMenu();
	}
	public void readPerson(Session session){
		
			Query query = (Query) session.createQuery("From Person Where FIRST_NAME = ? AND NAME=?");
			query.setParameter(0, person.getFIRST_NAME());
			query.setParameter(1, person.getNAME());
			
			List <Person> persons = query.list();
			if(persons.isEmpty()){
				createPerson(session);
			}
			else{
				System.out.println("Person Already Exists! Try again!");
				contractMenu();
			}	
	}
	public void readContract(Session session){
		Query query = (Query) session.createQuery("From Person Where FIRST_NAME = ? AND NAME=?");
		query.setParameter(0, person.getFIRST_NAME());
		query.setParameter(1, person.getNAME());
		
		List <Person> persons = query.list();
		if(persons.isEmpty()){
			System.out.println(person.getFIRST_NAME() + " " + person.getNAME() +" doesn't exist! Try again!");
			contractMenu();
		}
		else{
			for(Person ps :persons){
				this.person = ps;
			}
			if(this.getContract_type()=='P'){

				query = (Query) session.createQuery("From PurchaseContract where FIRST_NAME = ? AND NAME = ? AND ESTATE_ID=?");
				query.setParameter(0, person.getFIRST_NAME());
				query.setParameter(1, person.getNAME());
				query.setParameter(2, estate.getESTATE_ID());
				List <PurchaseContract> pcs = query.list();
				if(pcs.isEmpty()){
					autoGenerate(session);
					createContract(session);
				}
				
				else{
					System.out.println("Contract for the person-estate combination Already Exists! Try again!");
					contractMenu();
				}
			}
			else{
				query = (Query) session.createQuery("From TenancyContract where FIRST_NAME = ? AND NAME = ? AND ESTATE_ID=?");
				query.setParameter(0, person.getFIRST_NAME());
				query.setParameter(1, person.getNAME());
				query.setParameter(2, estate.getESTATE_ID());
				List <TenancyContract> tcs = query.list();
				if(tcs.isEmpty()){
					autoGenerate(session);
					createContract(session);
				}
				
				else{
					System.out.println("Contract for the person-estate combination Already Exists! Try again!");
					contractMenu();
				}
			}
		}
	}
	
	public void createContract(Session session){
		java.util.Date today = new java.util.Date();
		contract.setCONTRACT_DATE(new java.sql.Date(today.getTime()));
		session.save(contract);
		System.out.println("Contract " + contract.getCONTRACT_NO() + " added.");
						
		if(this.getContract_type()=='P'){
			pc.setPerson(person);
			pc.setContract(contract);
			pc.setHouse(house);
			session.save(pc);
				
			System.out.println("Purchase Contract " + pc.getContract().getCONTRACT_NO() + " added.");
								
		}
		else if(this.getContract_type()=='T'){
			
			tc.setPerson(person);
			tc.setContract(contract);
			tc.setApartment(apartment);
			session.save(tc);			
		
			System.out.println("Tenancy Contract  " + tc.getContract().getCONTRACT_NO() + " added.");
		
		}
		session.getTransaction().commit();
		contractMenu();
	}
	public void autoGenerate(Session session){
		
		String c_no = null;
	    Query query = (Query) session.createQuery("Select MAX(CONTRACT_NO) FROM Contract");
	    
        List<String> contracts = query.list();  
        if(contracts.isEmpty()){
        	c_no = "CT000";
        }
        else{
        	for(String con: contracts)  
        	{  
        		c_no = con;
        	}    
        }
	
		String regex = "(?<=[\\w&&\\D])(?=\\d)";
		String[] alphanumeric = c_no.split(regex);
		int numeric = Integer.parseInt(alphanumeric[1]);
		numeric = numeric+1;
		c_no = alphanumeric[0].concat(String.format("%03d", numeric));
		contract.setCONTRACT_NO(c_no);
	}
	
	public void viewPurchaseContracts(Session session)
	{
		System.out.println("-------------------------------------------------");
		System.out.println("------DISPLAYING ALL CONTRACTS --------------");
		System.out.println("-------------------------------------------------");
		
		Query query = (Query) session.createQuery("FROM TenancyContract");
		List <TenancyContract> tcss = query.list();
		if(tcss.isEmpty()){
			System.out.println("No Tenancy Contracts found!");
		}
		else{
			
			System.out.println("-------------------------------------------------");
			System.out.println("------DISPLAYING TENANCY CONTRACTS --------------");
			System.out.println("-------------------------------------------------");
			int i=1;
			for(TenancyContract tcc : tcss){
				System.out.println("-------------------------------------------------");
				System.out.println("Tenancy Contract : " + i++);
				System.out.println("-------------------------------------------------");
				System.out.println("CONTRACT_NO: " + tcc.getContract().getCONTRACT_NO());
				System.out.println("ESTATE_ID: " + tcc.getApartment().getEstate().getESTATE_ID());
				System.out.println("FIRST_NAME: " + tcc.getPerson().getFIRST_NAME());
				System.out.println("LAST_NAME: " + tcc.getPerson().getNAME());
				System.out.println("START_DATE: " + tcc.getSTART_DATE());
				System.out.println("DURATION: " + tcc.getDURATION());
				System.out.println("ADDITIONAL COST: " + tcc.getADD_COST());
			}
		}
			
		query = (Query) session.createQuery("FROM PurchaseContract");
		List <PurchaseContract> pcss = query.list();
		if(pcss.isEmpty()){
			System.out.println("No Purchase Contracts found!");
		}
		else{
			
			System.out.println("-------------------------------------------------");
			System.out.println("------DISPLAYING PURCHASE CONTRACTS --------------");
			System.out.println("-------------------------------------------------");
			int i=1;
			for(PurchaseContract pcc : pcss){
				System.out.println("-------------------------------------------------");
				System.out.println("Purchase Contract : " + i++);
				System.out.println("-------------------------------------------------");
				System.out.println("CONTRACT_NO: " + pcc.getContract().getCONTRACT_NO());
				System.out.println("ESTATE_ID: " + pcc.getHouse().getEstate().getESTATE_ID());
				System.out.println("FIRST_NAME: " + pcc.getPerson().getFIRST_NAME());
				System.out.println("LAST_NAME: " + pcc.getPerson().getNAME());
				System.out.println("INSTALLMENT: " + pcc.getINSTAL());
				System.out.println("INTEREST_RATE: " + pcc.getINSTAL());
			}		
		}
	
	}
//	public void viewSaleContracts()
//	{
//		DBConManager con = new DBConManager();
//		con.CreateConnection();
//		try{
//			
//			String sql;
//			sql = "SELECT * FROM PURCHASE_CONTRACT";
//			PreparedStatement pstm = con.getConnection().prepareStatement(sql);
//			System.out.println("-------------------------------------------------");
//			System.out.println("------DISPLAYING TENANCY CONTRACTS --------------");
//			System.out.println("-------------------------------------------------");
//			ResultSet rs = pstm.executeQuery();
//			ResultSetMetaData metaData = rs.getMetaData();
//			int columnCount = metaData.getColumnCount();
//			int recno = 1;;
//			while(rs.next()) {
//				System.out.println("-------------------------------------------------");
//				System.out.println("---- Purchase Contract S. No. " + recno +  "---------------");
//				System.out.println("-------------------------------------------------");
//				recno++;
//			    for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//			        String columnValue = rs.getString(columnIndex);
//
//			        System.out.println(metaData.getColumnName(columnIndex) + ": " +columnValue);
//
//			    }
//			    System.out.printf("\n");
//				System.out.println("-------------------------------------------------");
//
//			}
//			rs.close();
//			pstm.close();			
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		contractMenu();
//	}
	public String getloggedinUser() {
		return loggedinUser;
	}
	public void setloggedinUser(String loggedinUser) {
		ContractManagement.loggedinUser = loggedinUser;
	}
	public char getContract_type() {
		return contract_type;
	}
	public void setContract_type(char contract_type) {
		this.contract_type = contract_type;
	}
}
