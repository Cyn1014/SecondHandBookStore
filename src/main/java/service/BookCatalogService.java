package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Book;
import beans.Orders;
import beans.Seller;
import beans.dao.BookDAO;
import beans.dao.OrderDAO;
import beans.dao.SellerDAO;

@Service("bookcatalogService")
public class BookCatalogService {
	
	@Autowired
	private SellerDAO sellerDao;
	@Autowired
	private BookDAO bookDao;
	@Autowired
	private OrderDAO orderDao;
	
	public Seller getSeller(int id){
		
		 return sellerDao.findSellerById(id);
		
	}
	
	public List<Book> getBooks(){
		return bookDao.getBooks();
	}
	
	public List<Orders> getOrderBookBySellerid(int sid){
		
		return orderDao.findOrderBookBySellerId(sid);
		
	}
	
	public void addBook(Book book){
		
		 bookDao.persist(book);
		
	}
	
	public Book getBookById(int id){
		return bookDao.findBookById(id);
	}
	
	public void deleteBook(Book book) throws Exception{
		bookDao.delete(book);
	}
	
	public void updateBook(Book book) throws Exception{
		bookDao.update(book);
	}
	
	

}
