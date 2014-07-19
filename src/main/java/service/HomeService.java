package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Address;
import beans.Book;
import beans.Buyer;
import beans.Cart;
import beans.Orders;
import beans.Seller;
import beans.User;
import beans.dao.AddressDAO;
import beans.dao.BookDAO;
import beans.dao.BuyerDAO;
import beans.dao.CartDAO;
import beans.dao.OrderDAO;
import beans.dao.SellerDAO;
import beans.dao.UserDAO;

@Service("homeService")
public class HomeService {
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private BuyerDAO buyerDao;
	@Autowired
	private SellerDAO sellerDao;
	@Autowired
	private AddressDAO addressDao;
	@Autowired
	private BookDAO bookDao;
	@Autowired
	private CartDAO cartDao;
	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private MailControl mailControl;
	
	public List<Book> getBooks(){
		return bookDao.getBooks();
	}
	
	public Book getBook(int id){
		return bookDao.findBookById(id);
	}
	
	public User getUserByName(String name){
		return userDao.findUserByName(name);
	}
	public User getUser(int id){
		return userDao.findUserById(id);
	}
	
	
	public Seller getSeller(int id){
		
		 return sellerDao.findSellerById(id);
		
	}
	public Buyer getBuyer(int id){
		
		 return buyerDao.findBuyerById(id);
		
	}
	public Seller getSellerUser(int id){
		
		 return sellerDao.findSellerByUserId(id);
		
	}
	public Buyer getBuyerUser(int id){
		
		 return buyerDao.findBuyerByUserId(id);
		
	}
	
   public List<Orders> getOrderBookBySellerid(int sid){
		
		return orderDao.findOrderBookBySellerId(sid);
		
	}
    
   public List<Orders> getOrderBookByBuyerId(int bid){
		
		return orderDao.findOrderBookByBuyerId(bid);
		
	}
	
	public void saveUser(User user,Address address,Buyer buyer, Seller seller){
		
		userDao.persist(user);
		address.setUser(user);
		addressDao.persist(address);
		
		if(user.getRole().equalsIgnoreCase("buyer"))
		{
			Cart cart=new Cart();
			cart.setBuyer(buyer);
			buyer.setUser(user);
			buyer.setCart(cart);
			cartDao.persist(cart);
			buyerDao.persist(buyer);
			
		}else if(user.getRole().equalsIgnoreCase("seller"))
		{
			seller.setUser(user);
            sellerDao.persist(seller);
		}
		
		
	}
	
	public void updateUser(User user,Address address, Buyer buyer,Seller seller) throws Exception{
		userDao.update(user);
		address.setUser(user);
		addressDao.update(address);
		if(user.getRole().equalsIgnoreCase("buyer"))
		{
			
			buyer.setUser(user);
			buyer.setCart(buyerDao.findBuyerById(buyer.getBid()).getCart());
			buyerDao.persist(buyer);
			
		}else if(user.getRole().equalsIgnoreCase("seller"))
		{
			seller.setUser(user);
            sellerDao.update(seller);
		}
		
	}
	
	public String validate(User user,String rpassword){
		String error=null;
		if(userDao.findUserByName(user.getUsername())==null)
		{ 
			if(user.getPassword().length()>=4)
			{if(user.getPassword().equalsIgnoreCase(rpassword))
			{
				error=null;
			}else{
				
				error="Password does not match!";
			
			}
			}else{
				error="Password must be at least 4 digits"; 
			}
		
		}else{
			if(userDao.findUserByName(user.getUsername()).getUid()!=user.getUid())
			error="Username is exist!";
			}
		return error;
	}
	
	public void sendMail(String subject,String email,String message){
		message="From: "+email+" \n"+message;
		mailControl.sendMail("example@gmail.com", "liu.cong@husky.neu.edu", subject, message);
		
		
		
	}
	
	
	
	

}
