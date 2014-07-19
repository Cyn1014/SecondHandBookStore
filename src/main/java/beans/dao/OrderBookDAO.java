package beans.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.OrderBook;

@Repository
public class OrderBookDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void persist(OrderBook orderBook){
		Session session=sessionFactory.openSession();
		session.save(orderBook);
		session.close();
	}
	
	public void update(OrderBook orderBook) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.update(orderBook);
	  
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
	
	public void delete(OrderBook orderBook) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.delete(orderBook);
	  
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
	public OrderBook findOrderBookById(int id){
		
		Session session=sessionFactory.openSession();
		OrderBook orderBook =(OrderBook) session.get(OrderBook.class,id);
		session.close();
		return orderBook;
		
	}
}
