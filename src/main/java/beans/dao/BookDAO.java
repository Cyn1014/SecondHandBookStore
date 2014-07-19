package beans.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import beans.Book;

@Repository
public class BookDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void persist(Book book){
		Session session=sessionFactory.openSession();
		session.save(book);
		session.close();
	}
	
	public void delete(Book book) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.delete(book);
	  
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
	
	public void update(Book book) throws Exception{
		Session session=sessionFactory.openSession();
		Transaction tx = null;
	    try {
            tx = session.beginTransaction() ;
	    	
            session.update(book);
	  
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
	public Book findBookById(int id){
		Session session=sessionFactory.openSession();
		Book book =(Book) session.get(Book.class,id);
		session.close();
		return book;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Book> getBooks(){
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from Book");
		List<Book> books= query.list();
		session.close();
		return books;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Book> findBookByWord(String word,String type){
		Session session=sessionFactory.openSession();
		Query query=null;
		if(type.equalsIgnoreCase("title"))
		 query= session.createQuery("from Book as book where book.title LIKE ? ");
		else if(type.equalsIgnoreCase("isbn"))
			query= session.createQuery("from Book as book where book.isbn LIKE ? ");
		else if(type.equalsIgnoreCase("author"))
		query= session.createQuery("from Book as book where book.author LIKE ? ");
		query.setParameter(0, word);
		List<Book> books= query.list();
		session.close();
		return books;
	}
	
	

}
