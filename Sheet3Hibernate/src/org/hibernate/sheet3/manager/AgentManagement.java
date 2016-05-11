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
import org.hibernate.sheet3.domain.EstateAgent;
import org.hibernate.sheet3.util.HibernateUtil;

/**
 * @author nikhilchuramani
 *
 */
public class AgentManagement {

	/**
	 * 
	 */
	private EstateAgent eagent = new EstateAgent();
	private static String loggedinUser;
	
	public AgentManagement(String loggedin) {
		// TODO Auto-generated constructor stub
		AgentManagement.loggedinUser = loggedin;
		
	}

	public void agentMenu(){
		char choice = '0';
		System.out.println("-------------------------------------------------");
		System.out.println("------------AGENT MANAGEMENT MODULE--------------");
		System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Press 1 for Agent ID Creation");
		System.out.println("Press 2 for Update Agent Details");
		System.out.println("Press 3 for Delete an Agent");
		System.out.println("Press 4 to go back to Main Menu");

		try{
			 choice = (char) new BufferedReader(new InputStreamReader(System.in)).read();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (choice == '1'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("------CREATE AGENT MANAGEMENT MODULE--------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
			try{
				System.out.println("Enter AGENT NAME:");
				eagent.setNAME(br.readLine());
				System.out.println("Enter AGENT ADDRESS:");
				eagent.setADDR(br.readLine());
				System.out.println("Address: "+eagent.getADDR());
			}
			catch(IOException io){
				io.printStackTrace();
			}
			
			createAgent(session);
		}
		else if(choice=='2'){
			char choiceupdate = '0';
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("-----UPDATE AGENT MANAGEMENT MODULE--------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
			
			try{
				System.out.println("Enter AGENT LOGIN TO VIEW DETAILS:");
				eagent.setLOGIN(br.readLine());
				
				readAgent(session);
				
				System.out.println("Name: "+ eagent.getNAME());
				System.out.println("Address: " + eagent.getADDR());
				System.out.println("Login: " + eagent.getLOGIN());
				System.out.println("Password: "+ eagent.getPASSWORD());
				System.out.println("-------------------------------------------------");
				System.out.println("Press 1 for Edit Name");
				System.out.println("Press 2 for Edit Address");
				System.out.println("Press 3 for Edit Password");
				System.out.println("Note: Cannot edit LOGIN. Please contact DB Admin.");
				choiceupdate = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				
				if(choiceupdate=='1'){
					System.out.println("Enter New Name: ");
					eagent.setNAME(br.readLine());
				}
				else if(choiceupdate =='2'){
					System.out.println("Enter New Address: ");
					eagent.setADDR(br.readLine());
				}
				else if(choiceupdate =='3'){
					System.out.println("Enter New Password: ");
					eagent.setPASSWORD(br.readLine());
				}
				else{
					System.out.println("Invalid Choice! Try again!");
					agentMenu();
				}
				updateAgent(session);			
			}
			catch(IOException io){
				io.printStackTrace();
			}
			System.out.println("Agent details updated!");
			agentMenu();
		}

		else if(choice=='3'){
			System.out.println("-------------------------------------------------");
			System.out.println("------------WELCOME " + this.getloggedinUser() + "------------------------");
			System.out.println("-----DELETE AGENT MANAGEMENT MODULE--------------");
			System.out.println("-------------------------------------------------");
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
			
			try{
				System.out.println("Enter AGENT LOGIN TO DELETE:");
				eagent.setLOGIN(br.readLine());
				
				readAgent(session);
				
				System.out.println("CONFIRM AGENT DELETE (Y/N):");
				char choicedelete = (char) new BufferedReader(new InputStreamReader(System.in)).read();
				if(choicedelete == 'Y'){
					deleteAgent(session);
				}
				else if(choicedelete=='N'){
					System.out.println("Agent not deleted!");
					agentMenu();
				}
				else{
					System.out.println("Invalid Choice! Try Again!");
					agentMenu();
				}
			}
			catch(IOException io){
				io.printStackTrace();
			}
			System.out.println("Agent" + eagent.getLOGIN() + "deleted!");
			agentMenu();
		}
		else if(choice=='4'){
			Main.menuDisplay();
		}
		else{
			System.out.println("Invalid choice! Chose again!");
			agentMenu();
		}
	}
	
	public String getloggedinUser() {
		return loggedinUser;
	}
	public void setloggedinUser(String loggedinUser) {
		AgentManagement.loggedinUser = loggedinUser;
	}
	
	public void createAgent(Session session){
		autoGenerate(session);
		System.out.println("Agent with Login: "+eagent.getLOGIN()+" created!");
		session.save(eagent);
		session.getTransaction().commit();
		agentMenu();
	}
	public void autoGenerate(Session session){
		String loginid = null;
	    Query query = (Query) session.createQuery("Select MAX(LOGIN) FROM EstateAgent");
	    
        List<String> loginids = query.list();  
        if(loginids.isEmpty()){
        	loginid = "EA000";
        }
        else{
        	for(String loginvar: loginids)  
        	{  
        		loginid = loginvar;
        	}    
        }
	
		String regex = "(?<=[\\w&&\\D])(?=\\d)";
		String[] alphanumeric = loginid.split(regex);
		int numeric = Integer.parseInt(alphanumeric[1]);
		numeric = numeric+1;
		loginid = alphanumeric[0].concat(String.format("%03d", numeric));
		eagent.setLOGIN(loginid);
		eagent.setPASSWORD("AGENT".concat(loginid));
	}
	
	public void readAgent(Session session){
	    Query query = (Query) session.createQuery("FROM EstateAgent where LOGIN =?");
	    query.setParameter(0, eagent.getLOGIN());
	    List<EstateAgent> eagents =  query.list();  
        if(eagents.isEmpty()){
        	System.out.println("Invalid Login! Try again!");
			agentMenu();
        }
        else{
        	for(EstateAgent agent :eagents){
        		eagent = agent;
        	}
        }
	
	}
	public void updateAgent(Session session){
		session.save(eagent);
		session.getTransaction().commit();
	}
	public void deleteAgent(Session session){
//		Query query = (Query) session.createQuery("delete EstateAgent where LOGIN = ?");
//		query.setParameter(0, eagent.getLOGIN());
		session.delete(eagent);
		session.getTransaction().commit();
	}
}
