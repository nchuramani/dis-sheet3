package org.hibernate.sheet3.util;
 
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateUtil {
 
    private static final SessionFactory sessionFactory = buildSessionFactory();
 
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration cfg = new Configuration();
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.Estate.class);
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.Apartment.class);
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.Contract.class);
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.EstateAgent.class);
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.House.class);
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.PersonPK.class);
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.PurchaseContract.class);
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.TenancyContract.class);
        	cfg.addAnnotatedClass(org.hibernate.sheet3.domain.Person.class);

        	return cfg.configure().buildSessionFactory();        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
}