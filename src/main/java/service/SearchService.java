package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import beans.Address;
import beans.Book;
import beans.Buyer;
import beans.Feed;
import beans.Item;
import beans.Orders;
import beans.Seller;
import beans.dao.BookDAO;
import beans.dao.BuyerDAO;
import beans.dao.OrderDAO;
import beans.dao.SellerDAO;

@Service("searchService")
public class SearchService {
	@Autowired
	private BuyerDAO buyerDao;
	@Autowired
	private SellerDAO sellerDao;
	@Autowired
	private BookDAO bookDao;
	@Autowired
	private OrderDAO orderDao;
	
	public List<Book> getBooks(){
		return bookDao.getBooks();
	}
	
	public List<Book> getBookByWord(String word,String type){
		return bookDao.findBookByWord(word, type);
	}
	
	public Seller getSeller(int id){
		
		 return sellerDao.findSellerById(id);
		
	}
	
    public List<Orders> getOrderBookBySellerid(int sid){
		
		return orderDao.findOrderBookBySellerId(sid);
		
	}
    
   public List<Orders> getOrderBookByBuyerId(int sid){
		
		return orderDao.findOrderBookByBuyerId(sid);
		
	}
    
    public Book getBookById(int id){
		return bookDao.findBookById(id);
	}
    
    public Buyer getBuyer(int id){
		
		 return buyerDao.findBuyerById(id);
		
	}
    
    public Feed getNews(Seller seller) throws ClientProtocolException, IOException{
    	String company=seller.getCompany().replaceAll(" ", "+");
		HttpClient httpClient=new DefaultHttpClient();
		String url="http://news.google.com/news?q="+company+"&output=rss";
		HttpPost httpPost=new HttpPost(url);
		HttpResponse responseR=httpClient.execute(httpPost);
		InputStream input=responseR.getEntity().getContent();
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=null;
		Feed feed=new Feed();
		try {
			builder=factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document doc=null;
		
		try {
			doc=builder.parse(input);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(doc!=null)
		{
			
			doc.normalize();
			feed.setTitle(doc.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());
			feed.setDescription(doc.getElementsByTagName("description").item(doc.getElementsByTagName("description").getLength()-1).getFirstChild().getNodeValue());
			NodeList nl=doc.getElementsByTagName("item");
			for(int i=0;i<nl.getLength();i++){
				Element e=(Element)nl.item(i);
				Item item=feed.addItem();
				item.setTitle(e.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());
				item.setLink(e.getElementsByTagName("link").item(0).getFirstChild().getNodeValue());
				item.setPubDate(e.getElementsByTagName("pubDate").item(0).getFirstChild().getNodeValue());
			}
			
			
		}
		return feed;
    	
    }
	
	public String getMapUrl(Seller seller){
		String url=null;
		Address address=seller.getUser().getAddressList().get(0);
		String location=(address.getStreet()+","+address.getCity()+","+address.getState()).replaceAll(" ", "+");
		url="http://maps.googleapis.com/maps/api/staticmap?center="+location+"&zoom=13&size=450x300&markers=color:blue%7C"+location+"&sensor=false&key=AIzaSyAWWnv967S3xALFC15KMkLP8u4LehMQTG4";
		return url;
	}
	
	public List<Book> getRelatedBook(int bid){
		Book book=bookDao.findBookById(bid);
		List<Book> books=bookDao.findBookByWord(book.getAuthor(), "author");
		
		for(Book b:books){
			if(b.getBid()==book.getBid()){
				books.remove(b);
				break;
			}
		}
		return books;
		
	}
	

}
