package org.hibernate.sheet3.manager;
 

import org.hibernate.Session;
import org.hibernate.sheet3.domain.*;
import org.hibernate.sheet3.util.HibernateUtil;

import java.util.*;

import org.hibernate.Query;

public class EventManager {

    public static void main(String[] args) {
        EventManager mgr = new EventManager();

        mgr.createAndStoreEvent();
    

        HibernateUtil.getSessionFactory().close();
    }

    private void createAndStoreEvent() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = (Query) session.createQuery("FROM TenancyContract where ESTATE_ID =?");
        query.setParameter(0, "ES003");
        List<TenancyContract> tcontracts = query.list();   
        for(TenancyContract tcontract : tcontracts)  
        {  
        	System.out.println("EstateID "+tcontract.getApartment().getEstate().getESTATE_ID()+"Agent "+tcontract.getApartment().getEstate().getAgent().getLOGIN() + "ContractNo: "+tcontract.getContract().getCONTRACT_NO()+ "First Name: "+ tcontract.getPerson().getFIRST_NAME());  
        }          
        session.getTransaction().commit();
    }
    
}