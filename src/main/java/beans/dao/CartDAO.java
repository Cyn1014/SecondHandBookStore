package beans.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.Cart;
import beans.OrderBook;

@Repository
public class CartDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void persist(Cart cart){
		Session session=sessionFactory.openSession();
		session.save(cart);
		session.close();
	}
	
	public void update(Cart cart) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.update(cart);
	  
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
	public Cart findCartById(int id){
		
		Session session=sessionFactory.openSession();
		Cart cart =(Cart) session.get(Cart.class,id);
		session.close();
		return cart;
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<OrderBook> getAllOrderBook(int id){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from OrderBook as ob where ob.cart.cid= ?");
	    query.setParameter(0, id);
	    List<OrderBook> books = query.list();
	    
		session.close();
		return books;
	}


}
