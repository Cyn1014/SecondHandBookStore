package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Address;
import beans.Book;
import beans.Buyer;
import beans.Cart;
import beans.OrderBook;
import beans.Orders;
import beans.Seller;
import beans.dao.AddressDAO;
import beans.dao.BookDAO;
import beans.dao.BuyerDAO;
import beans.dao.CartDAO;
import beans.dao.OrderBookDAO;
import beans.dao.OrderDAO;
import beans.dao.SellerDAO;

@Service("orderService")
public class OrderService {
	
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
	private OrderBookDAO orderbookDao;
	@Autowired
	private OrderDAO orderDao;
	
	 public List<Orders> getOrderBookBySellerid(int sid){
			
			return orderDao.findOrderBookBySellerId(sid);
			
		}
	    
	   public List<Orders> getOrderBookByBuyerId(int sid){
			
			return orderDao.findOrderBookByBuyerId(sid);
			
		}
	   public Seller getSeller(int id){
			
			 return sellerDao.findSellerById(id);
			
		}
		public Buyer getBuyer(int id){
			
			 return buyerDao.findBuyerById(id);
			
		}
		
		public List<Book> getBooks(){
			return bookDao.getBooks();
		}
		
		public Orders getOrder(int oid){
			return orderDao.findOrderById(oid);
			
		}
		
		public void updateOrder(int sid,Orders oneorder) throws Exception{
			Orders order=orderDao.findOrderById(oneorder.getOid());
			
			order.setIsreturn(oneorder.isIsreturn());
			order.setStatus(oneorder.getStatus());
			order.setTracking(oneorder.getTracking());
			
			orderDao.update(order);
			
		}
	
	public void addToCart(int bkid, int bid) throws Exception{
		
		Buyer buyer=buyerDao.findBuyerById(bid);
		Cart cart=buyer.getCart();
		Book book=bookDao.findBookById(bkid);
		OrderBook orderbook=new OrderBook();
		orderbook.setBook(book);
		orderbook.setQuantity(1);
		orderbook.setCart(cart);
		cart.setBuyer(buyer);
		List<OrderBook> books=cartDao.getAllOrderBook(cart.getCid());
		boolean has=false;
		if(books.isEmpty())
			{books.add(orderbook); has=true;}
		else{
			for(OrderBook b:books){
				if(b.getBook().getBid()==orderbook.getBook().getBid()){
					has=true;
					if(b.getQuantity()<b.getBook().getQuantity()){
						b.setQuantity(b.getQuantity()+orderbook.getQuantity());
					}
					break;
				}
			}
		}
		if(!has)books.add(orderbook);
			cart.setOrderbooks(books);
		
		    cartDao.update(cart);
		
	}
	
	public void modifiedQuantity(int bid,int obid,String method) throws Exception{
		if(method.equalsIgnoreCase("add")){
			OrderBook ob=orderbookDao.findOrderBookById(obid);
			if(ob.getQuantity()<ob.getBook().getQuantity())
			{ob.setQuantity(ob.getQuantity()+1);
			orderbookDao.update(ob);}
		}else if(method.equalsIgnoreCase("minus")){
			OrderBook ob=orderbookDao.findOrderBookById(obid);
			if(ob.getQuantity()>1)
			{
			ob.setQuantity(ob.getQuantity()-1);
			orderbookDao.update(ob);}
			else orderbookDao.delete(ob);
			
		}
	}
	public void checkout(Address address,int bid) throws Exception{
		
		Buyer buyer=buyerDao.findBuyerById(bid);
		Cart cart=cartDao.findCartById(buyer.getCart().getCid());
		List<OrderBook> obs=cartDao.getAllOrderBook(cart.getCid());
		addressDao.persist(address);
		List<Orders> ol=new ArrayList<Orders>();
		for(OrderBook ob:obs){
			if(ol.isEmpty()){
			  Orders order=new Orders();
		      order.setBuyer(buyer);
		      order.setIsreturn(false);
		      order.setStatus("pending");
		      order.setAddress(address);
		      order.setSeller(ob.getBook().getSeller());
		      orderDao.persist(order);
		      List<OrderBook> list=new ArrayList<OrderBook>();
		      list.add(ob);
		      order.setBooks(list);
		      ob.setCart(null);
		      ob.setOrder(order);
		      orderbookDao.update(ob);
		      ob.getBook().setQuantity(ob.getBook().getQuantity()-ob.getQuantity());
		      bookDao.update(ob.getBook());
		      ol.add(order);
			}else{
				boolean has=false;
				for(Orders o:ol){
					if(ob.getBook().getSeller().getSid()==o.getSeller().getSid())
					{
						o.getBooks().add(ob);
						ob.setOrder(o);
						ob.setCart(null);
						orderbookDao.update(ob);
						ob.getBook().setQuantity(ob.getBook().getQuantity()-ob.getQuantity());
					      bookDao.update(ob.getBook());
						has=true;
						
					}
				}
				if(!has){
					  Orders order=new Orders();
				      order.setBuyer(buyer);
				      order.setIsreturn(false);
				      order.setAddress(address);
				      order.setStatus("pending");
				      order.setSeller(ob.getBook().getSeller());
				      orderDao.persist(order);
				      List<OrderBook> list=new ArrayList<OrderBook>();
				      list.add(ob);
				      order.setBooks(list);
				      ob.setOrder(order);
				      ob.setCart(null);
				      orderbookDao.update(ob);
				      ob.getBook().setQuantity(ob.getBook().getQuantity()-ob.getQuantity());
				      bookDao.update(ob.getBook());
				      ol.add(order);
					
				}
			}
			
			
		}
		
	}

}
