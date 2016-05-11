/**
 * 
 */
package org.hibernate.sheet3.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.sheet3.Main;
import org.hibernate.sheet3.domain.*;
import org.hibernate.sheet3.util.HibernateUtil;

/**
 * @author nikhilchuramani
 *
 */
public class EstateManagement{

	private static String loggedinUser;
	private char estate_type;
	private Estate estate;
	private Apartment apartment;
	private House house;
	
	public EstateManagement(String loggedin) {
		// TODO Auto-generated constructor stub
		EstateManagement.loggedinUser = loggedin;
		estate = new Estate();
		apartment = new Apartment();
		house = new House();
		
	}
	
	public void estateMenu(){
		char choice = '0';
		System.out.println("-------------------------------------------------");
		System.out.println("------------ESTATE MANAGEMENT MODULE--------------");
		System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Press 1 for Estate Creation");
		System.out.println("Press 2 for Update Estate Details");
		System.out.println("Press 3 for Delete an Estate");
		System.out.println("Press 4 to go back to Main Menu");

		try{
			 choice = (char) new BufferedReader(new InputStreamReader(System.in)).read();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (choice == '1'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------CREATE ESTATE MANAGEMENT MODULE------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);

			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
		    Query query = (Query) session.createQuery("FROM EstateAgent Where LOGIN =?");
		    query.setParameter(0, this.getloggedinUser());
		    List<EstateAgent> eagents = query.list();
		    if(eagents.isEmpty()){
		    	System.out.println("Invalid Logged in User!");
		    	estateMenu();
		    }
		    else{
		    	for(EstateAgent eagent : eagents){
		    		this.estate.setAgent(eagent);
		    	}
		    }
		    

			try{
				System.out.println("Enter STREET NUMBER:");
				estate.setSTREET_NUMBER(Integer.parseInt(br.readLine()));
				System.out.println("Enter STREET:");
				estate.setSTREET(br.readLine());
				System.out.println("Enter CITY:");
				estate.setCITY(br.readLine());
				System.out.println("Enter POSTAL CODE:");
				estate.setPOSTAL_CODE(br.readLine());
				System.out.println("Enter Sq. AREA:");
				estate.setSQUARE_AREA(Float.parseFloat(br.readLine()));
				estate.getAgent().setLOGIN(this.getloggedinUser());
				System.out.println("Estate Type House (H) of Apartment (A):");
				char h_a = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				this.setEstate_type(h_a);
				if(this.getEstate_type()=='H'){
					house.setEstate(estate);
					System.out.println("Enter number of floors for House:");
					house.setFLOORS(Integer.parseInt(br.readLine()));
					System.out.println("Enter price for House:");
					house.setPRICE(Double.parseDouble(br.readLine()));
					System.out.println("Garder (Y/N):");
					house.setGARDEN((char) br.read());					
				}
				else if(this.getEstate_type()=='A'){
					apartment.setEstate(estate);
					System.out.println("Enter floor number for Apartment:");
					apartment.setFLOOR(Integer.parseInt(br.readLine()));
					System.out.println("Enter Rent for Apartment:");
					apartment.setRENT(Double.parseDouble(br.readLine()));

					System.out.println("Enter number of rooms for Apartment:");
					apartment.setROOMS(Integer.parseInt(br.readLine()));	
					
					System.out.println("Balcony (Y/N):");
					apartment.setBALCONY((char) br.read());
					char blank = (char) br.read();
					System.out.println("Kitchen (Y/N):");
					apartment.setKITCHEN((char) br.read());	
					
					
				}
				else{
					System.out.println("Invalid Choice! Try again!");
					estateMenu();
				}
			}
			catch(IOException io){
				io.printStackTrace();
			}
			createEstate(session);
		}
		else if(choice=='2'){
			char choiceupdate = '0';
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("-----UPDATE ESTATE MANAGEMENT MODULE-------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
		
			try{
				System.out.println("Enter ESTATE ID TO VIEW DETAILS:");
				estate.setESTATE_ID(br.readLine());
				
				readEstate(session);
				
				
				if(this.getEstate_type()=='H'){
					System.out.println("EstateID: "+ house.getEstate().getESTATE_ID());
					System.out.println("City: " + house.getEstate().getCITY());
					System.out.println("Post Code: " + house.getEstate().getPOSTAL_CODE());
					System.out.println("Street: "+ house.getEstate().getSTREET());
					System.out.println("Street No: "+ house.getEstate().getSTREET_NUMBER());
					System.out.println("Sq. Area: "+ house.getEstate().getSQUARE_AREA());
					System.out.println("Agent Login: "+ house.getEstate().getAgent().getLOGIN());
					System.out.println("Floors: "+ house.getFLOORS());
					System.out.println("Price: " + house.getPRICE());
					System.out.println("Garden: " + house.getGARDEN());					
				}
				else{
					System.out.println("EstateID: "+ apartment.getEstate().getESTATE_ID());
					System.out.println("City: " + apartment.getEstate().getCITY());
					System.out.println("Post Code: " + apartment.getEstate().getPOSTAL_CODE());
					System.out.println("Street: "+ apartment.getEstate().getSTREET());
					System.out.println("Street No: "+ apartment.getEstate().getSTREET_NUMBER());
					System.out.println("Sq. Area: "+ apartment.getEstate().getSQUARE_AREA());
					System.out.println("Agent Login: "+ apartment.getEstate().getAgent().getLOGIN());
					System.out.println("Floor No: "+ apartment.getFLOOR());
					System.out.println("Rent: " + apartment.getRENT());
					System.out.println("Number of Rooms: " + apartment.getROOMS());
					System.out.println("Balcony: " + apartment.getBALCONY());
					System.out.println("Kitchen: " + apartment.getKITCHEN());
				}

				System.out.println("-------------------------------------------------");
				System.out.println("Press 1 for Edit City");
				System.out.println("Press 2 for Edit PostCode");
				System.out.println("Press 3 for Edit Street");
				System.out.println("Press 4 for Edit Street No.");
				System.out.println("Press 5 for Edit Sq. Area");
				System.out.println("Press 6 for Edit Agent Login");
				
				if(this.getEstate_type()=='H'){
					
					System.out.println("Press 7 for Edit House Floors");
					System.out.println("Press 8 for Edit House Price");
					System.out.println("Press 9 for Edit Garden");
								
				}
				else{
					
					System.out.println("Press 7 for Edit Floor No.");
					System.out.println("Press 8 for Edit Rent.");
					System.out.println("Press 9 for Edit Number of Rooms");
					System.out.println("Press B for Edit Balcony");
					System.out.println("Press K for Edit Kitchen");

				}
				

				System.out.println("Note: Cannot edit Estate ID. Please contact DB Admin.");
				choiceupdate = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				
				if(choiceupdate=='1'){
					System.out.println("Enter New City: ");
					estate.setCITY(br.readLine());
				}
				else if(choiceupdate =='2'){
					System.out.println("Enter New Post Code: ");
					estate.setPOSTAL_CODE(br.readLine());
				}
				else if(choiceupdate =='3'){
					System.out.println("Enter New Street: ");
					estate.setSTREET(br.readLine());
				}
				else if(choiceupdate =='4'){
					System.out.println("Enter New Street No: ");
					estate.setSTREET_NUMBER(Integer.parseInt(br.readLine()));
				}
				else if(choiceupdate =='5'){
					System.out.println("Enter New Sq. Area: ");
					estate.setSQUARE_AREA(Float.parseFloat(br.readLine()));
				}
				else if(choiceupdate =='6'){
					System.out.println("Enter New Agent ID: ");
					EstateAgent eagent = new EstateAgent();
					eagent.setLOGIN(br.readLine());
					Query query = (Query) session.createQuery("FROM EstateAgent Where LOGIN =?");
				    query.setParameter(0, eagent.getLOGIN());
				    List<EstateAgent> eagents = query.list();
				    if(eagents.isEmpty()){
				    	System.out.println("Invalid Logged in User!");
				    	estateMenu();
				    }
				    else{
				    	for(EstateAgent eagent1 : eagents){
				    		this.estate.setAgent(eagent1);
				    	}
				    }
				}
				else if(choiceupdate =='7'){
					if(this.getEstate_type()=='H'){
						System.out.println("Enter New number of House Floors: ");
						house.setFLOORS(Integer.parseInt(br.readLine()));
					}
					else{
						System.out.println("Enter Floor Number for Apartment: ");
						apartment.setFLOOR(Integer.parseInt(br.readLine()));
					}
				}
				else if(choiceupdate =='8'){
					if(this.getEstate_type()=='H'){
						System.out.println("Enter New House Price: ");
						house.setPRICE(Double.parseDouble(br.readLine()));
						
					}
					else{
						System.out.println("Enter New Rent for Apartment: ");
						apartment.setRENT(Double.parseDouble(br.readLine()));
					}
				}
				else if(choiceupdate =='9'){
					if(this.getEstate_type()=='H'){
						System.out.println("Garden (Y/N): ");
						house.setGARDEN((char)(br.read()));
					}
					else{
						System.out.println("Enter Number of rooms for Apartment: ");
						apartment.setROOMS(Integer.parseInt(br.readLine()));
					}
				}
				else if(choiceupdate =='B'){
					if(this.getEstate_type()=='H'){
						System.out.println("Invalid Choice! Try Again! ");
						estateMenu();
					}
					else{
						System.out.println("Balcony (Y/N): ");
						apartment.setBALCONY((char)br.read());
					}
				}
				else if(choiceupdate =='K'){
					if(this.getEstate_type()=='H'){
						System.out.println("Invalid Choice! Try Again! ");
						estateMenu();
					}
					else{
						System.out.println("Kitchen (Y/N): ");
						apartment.setKITCHEN((char)br.read());
					}
				}
				else{
					System.out.println("Invalid Choice! Try again!");
					estateMenu();
				}
				updateEstate(session);			
			}
			catch(IOException io){
				io.printStackTrace();
			}
			System.out.println("Estate details updated!");
			estateMenu();
		}

		else if(choice=='3'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("-----DELETE ESTATE MANAGEMENT MODULE-------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
		
		    try{
				System.out.println("Enter ESTATE ID TO DELETE:");
				estate.setESTATE_ID(br.readLine());
				
				readEstate(session);
				
				System.out.println("CONFIRM AGENT DELETE (Y/N):");
				char choicedelete = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				if(choicedelete == 'Y'){
					deleteEstate(session);
				}
				else if(choicedelete=='N'){
					System.out.println("Estate not deleted!");
					estateMenu();
				}
				else{
					System.out.println("Invalid Choice! Try Again!");
					estateMenu();
				}
			}
			catch(IOException io){
				io.printStackTrace();
			}
		    if(this.getEstate_type()=='H'){
				System.out.println("House: " + estate.getESTATE_ID() + " deleted!");
		    }
		    else if(this.getEstate_type()=='A'){
				System.out.println("Apartment: " + estate.getESTATE_ID() + " deleted!");
		    }
		    else{
				System.out.println("ESTATE " + estate.getESTATE_ID() + " deleted!");
		    }
			estateMenu();
		}
		else if(choice=='4'){
			Main.menuDisplay();
		}
		else{
			System.out.println("Invalid choice! Chose again!");
			estateMenu();
		}
	}

	public void createEstate(Session session){
		autoGenerate(session);	
		session.save(estate);
		if(this.getEstate_type()=='H'){
			
			session.save(house);
			System.out.println("House " + house.getEstate().getESTATE_ID() + "added.");
		}
		else if(this.getEstate_type()=='A'){
			
			session.save(apartment);
			System.out.println("Apartment " + apartment.getEstate().getESTATE_ID() + "added.");

			}
		session.getTransaction().commit();
		estateMenu();
	}
	public void autoGenerate(Session session){
		String e_id = null;
	    Query query = (Query) session.createQuery("Select MAX(ESTATE_ID) FROM Estate");
	    
        List<String> loginids = query.list();  
        if(loginids.isEmpty()){
        	e_id = "ES000";
        }
        else{
        	for(String loginvar: loginids)  
        	{  
        		e_id = loginvar;
        	}    
        }
	
		String regex = "(?<=[\\w&&\\D])(?=\\d)";
		String[] alphanumeric = e_id.split(regex);
		int numeric = Integer.parseInt(alphanumeric[1]);
		numeric = numeric+1;
		e_id = alphanumeric[0].concat(String.format("%03d", numeric));
		estate.setESTATE_ID(e_id);
	}
	
	public void readEstate(Session session){
		
		String estateidd = estate.getESTATE_ID();
		Query query = (Query) session.createQuery("FROM Estate where ESTATE_ID =?");
		query.setParameter(0, estateidd);

		List<Estate> estates =  query.list();  
		if(estates.isEmpty()){
			System.out.println("Invalid Estate ID! Try again!");
			estateMenu();
		}
		else{
			for(Estate estate :estates){
				this.estate = estate;
			}
		}
		
		query = (Query) session.createQuery("FROM House where ESTATE_ID =?");
		query.setParameter(0, estateidd);
		List<House> houses =  query.list();  
		if(houses.isEmpty()){
			
			query = (Query) session.createQuery("FROM Apartment where ESTATE_ID =?");
			query.setParameter(0, estateidd);
			List<Apartment> apartments =  query.list();  
			if(apartments.isEmpty()){
				System.out.println("Not a house or an Apartment! Try again!");
				estateMenu();
			}
			else{
				for(Apartment apartment :apartments){
					this.apartment = apartment;
				}
//				this.apartment.setEstate(this.estate);
				this.setEstate_type('A');
			}
		}
		else{
			for(House house :houses){
				this.house = house;
			}
//			this.house.setEstate(this.estate);
			this.setEstate_type('H');
		}
	}
	
	public void updateEstate(Session session){
		
		if(this.getEstate_type()=='H'){
			house.setEstate(this.estate);
			session.save(estate);
			session.save(house);
		}
		else{
			apartment.setEstate(this.estate);
			session.save(estate);
			session.save(apartment);
		}
		session.getTransaction().commit();
		estateMenu();
	}
	
	public void deleteEstate(Session session){
		
		if(this.getEstate_type()=='H'){
		
			Query query = (Query) session.createQuery("From PurchaseContract where ESTATE_ID=?");
			query.setParameter(0, house.getEstate().getESTATE_ID());
			List<PurchaseContract> pcontr = query.list();
			if(!pcontr.isEmpty()){
				for(PurchaseContract pcr : pcontr){
					Contract ctr = pcr.getContract();
					session.delete(pcr);
					session.delete(ctr);
					
				}
				System.out.println("PURCHASE CONTRACT FOR THE HOUSE TERMINATED AND DELETED!");
			}
			session.delete(house);
		}
		else{
			Query query = (Query) session.createQuery("From TenancyContract where ESTATE_ID=?");
			query.setParameter(0, apartment.getEstate().getESTATE_ID());
			List<TenancyContract> tcontr = query.list();
			if(!tcontr.isEmpty()){
				for(TenancyContract tcr : tcontr){
					Contract ctr = tcr.getContract();
					session.delete(tcr);
					session.delete(ctr);
				}
			System.out.println("TENANCY CONTRACT FOR THE APARTMENT TERMINATED AND DELETED!");

			}
			session.delete(apartment);
		}
		
		session.delete(estate);
		session.getTransaction().commit();
	}
	
	
	public String getloggedinUser() {
		return loggedinUser;
	}
	public void setloggedinUser(String loggedinUser) {
		EstateManagement.loggedinUser = loggedinUser;
	}
	
	public static String getLoggedinUser() {
		return loggedinUser;
	}

	public static void setLoggedinUser(String loggedinUser) {
		EstateManagement.loggedinUser = loggedinUser;
	}

	public char getEstate_type() {
		return estate_type;
	}

	public void setEstate_type(char estate_type) {
		this.estate_type = estate_type;
	}

	
}
