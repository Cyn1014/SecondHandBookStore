package beans.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.Orders;

@Repository
public class OrderDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void persist(Orders order){
		Session session=sessionFactory.openSession();
		session.save(order);
		session.close();
	}

	public void update(Orders order) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.update(order);
	  
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
	public Orders findOrderById(int id){
		Session session=sessionFactory.openSession();
		Orders order =(Orders) session.get(Orders.class,id);
		session.close();
		return order;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Orders> findOrderBookByBuyerId(int id){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from Orders as o where o.buyer.bid=?");
	    query.setParameter(0, id);
	    List<Orders> orderlist = query.list();
	    
		session.close();
		return orderlist;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Orders> findOrderBookBySellerId(int id){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from Orders as o where o.seller.sid=?");
	    query.setParameter(0, id);
	    List<Orders> orderlist = query.list();
	    
		session.close();
		return orderlist;
	}

}
