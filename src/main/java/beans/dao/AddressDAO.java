package beans.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.Address;

@Repository
public class AddressDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void persist(Address address){
		Session session=sessionFactory.openSession();
		session.save(address);
		session.close();
	}
	
	
	public void update(Address address) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.update(address);
	  
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
	public Address findAddressById(int id){
		Session session=sessionFactory.openSession();
		Address address =(Address) session.get(Address.class,id);
		session.close();
		return address;
	}

}
