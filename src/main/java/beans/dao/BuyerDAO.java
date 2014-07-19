package beans.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.Buyer;

@Repository
public class BuyerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void persist(Buyer buyer){
		Session session=sessionFactory.openSession();
		session.save(buyer);
		session.close();
	}
	
	public void update(Buyer buyer) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.update(buyer);
	  
            tx.commit() ; 
        } catch (Exception e) {
            if (tx != null) {
                
                tx.rollback( ) ;
            }
            throw e;
        } finally {
            session.close() ;
        }
	}
	@Transactional
	public Buyer findBuyerById(int id){
		Session session=sessionFactory.openSession();
		Buyer buyer =(Buyer) session.get(Buyer.class,id);
		session.close();
		return buyer;
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public Buyer findBuyerByUserId(int id){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from Buyer as buyer where buyer.user.uid= ?");
	    query.setParameter(0, id);
	    List buyers = query.list();
	    Buyer buyer=null;
		if(!buyers.isEmpty()){
			buyer=(Buyer)buyers.get(0);
		}
		session.close();
		return buyer;
	}
	
	

}
