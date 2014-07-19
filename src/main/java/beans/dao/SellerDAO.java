package beans.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.Seller;

@Repository
public class SellerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void persist(Seller seller){
		Session session=sessionFactory.openSession();
		session.save(seller);
		session.close();
	}
	
	public void update(Seller seller) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.update(seller);
	  
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
	public Seller findSellerById(int id){
		Session session=sessionFactory.openSession();
		Seller seller =(Seller) session.get(Seller.class,id);
		session.close();
		return seller;
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public Seller findSellerByUserId(int id){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from Seller as seller where seller.user.uid= ?");
	    query.setParameter(0, id);
	    List sellers = query.list();
	    Seller seller=null;
		if(!sellers.isEmpty()){
			seller=(Seller)sellers.get(0);
		}
		session.close();
		return seller;
	}

}
