/**
 * 
 */
package org.hibernate.sheet3;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.sheet3.domain.*;
import org.hibernate.sheet3.util.HibernateUtil;

/**
 * @author nikhilchuramani
 * 
 */
public class Login {
	
	/**
	 * Login class is used to authenticate the logged in Estate_Agent.
	 * The user enters the username and password in the Main.java class which is
	 * passed to the executeLogin method. 
	 * A database connection is made using the DBConManager class and the credentials
	 * of the user are verified. The result of the query is returned to the Main.java
	 * class.
	 */
	
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	public String executeLogin(String username, String password){
		/**
		 * executeLogin method is used to check for the credentials entered by the
		 * user to be present in the database.
		 */
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    String loggedin = null;
	    Query query = (Query) session.createQuery("FROM EstateAgent where LOGIN =? and PASSWORD =?");
	    query.setParameter(0, username);
	    query.setParameter(1, password);

        List<EstateAgent> agents = query.list();  
        if(agents.isEmpty()){
        	System.out.println("Invalid Login");
        	loggedin = null;
        }
        else{
        	for(EstateAgent agent : agents)  
        	{  
        		loggedin = agent.getLOGIN();  
        	}    
        }
        session.getTransaction().commit();
		return loggedin;			
	}
}
