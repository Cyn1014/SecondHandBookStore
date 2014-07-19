package beans.dao;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.User;

@Repository
public class UserDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void persist(User user){
		Session session=sessionFactory.openSession();
		session.save(user);
		session.close();
	}
	public void update(User user) throws Exception{
		
		
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.update(user);
	  
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
	public User findUserById(int id){
		Session session=sessionFactory.openSession();
		User user =(User) session.get(User.class,id);
		session.close();
		return user;
	}
	@SuppressWarnings("rawtypes")
	@Transactional
	public User findUserByName(String name){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from User as user where user.username = ?");
	    query.setParameter(0, name);
	    List users = query.list();
	    User user=null;
		if(!users.isEmpty()){
			user=(User)users.get(0);
		}
		session.close();
		return user;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getUserNameList(){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from User as user");
	    List<User> users = query.list();
	    List<String> usernamelist=new ArrayList<String>();
	    if(!users.isEmpty()){
	    	for(User u:users){
	    		usernamelist.add(u.getUsername());
	    	}
	    }
	    
		session.close();
		return usernamelist;
	}

}
