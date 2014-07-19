package com.shbc.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import service.BookCatalogService;

import beans.Book;



@Controller
public class BookCatalogController {
	
	@Autowired
	private BookCatalogService bookcatalogService;
	
	private InputStream inputStream=null;
	
	@RequestMapping(value="/backbook",method=RequestMethod.GET,params="sid")
	public String backbook(Model model,@RequestParam int sid){
		model.addAttribute("seller", bookcatalogService.getSeller(sid));
		model.addAttribute("index",2);
		model.addAttribute("catalog", "catalog");
		model.addAttribute("pages", (bookcatalogService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", bookcatalogService.getBooks().subList(0, 4));
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", bookcatalogService.getOrderBookBySellerid(sid));
    	model.addAttribute("num",3);
    	model.addAttribute("page", 1);
		return "seller";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/addbook", method=RequestMethod.POST)
	public String addbook(Model model,Book book,@RequestParam int sid){
		book.setSeller(bookcatalogService.getSeller(sid));
		try {
			book.setFrondpage(org.hibernate.Hibernate.createBlob(inputStream));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bookcatalogService.addBook(book);
		
		model.addAttribute("seller", bookcatalogService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
		model.addAttribute("index",2);
		model.addAttribute("pages", (bookcatalogService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", bookcatalogService.getBooks().subList(0, 4));
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", bookcatalogService.getOrderBookBySellerid(sid));
    	model.addAttribute("num",3);
    	model.addAttribute("page", 1);
		return "seller";
	}
	
	
	@RequestMapping(value="/addnewbook", method=RequestMethod.GET,params="sid")
	public String addnewbook(Model model,@RequestParam int sid){
		
		model.addAttribute("seller", bookcatalogService.getSeller(sid));
		model.addAttribute("catalog", "addbook");
		model.addAttribute("index",2);
		model.addAttribute("pages", (bookcatalogService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", bookcatalogService.getBooks().subList(0, 4));
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", bookcatalogService.getOrderBookBySellerid(sid));
    	model.addAttribute("num",3);
    	model.addAttribute("page", 1);
		return "seller";
	}

	
	
	@RequestMapping(value="/viewbook", method=RequestMethod.GET,params="bid")
	public String viewbook(Model model,@RequestParam int bid){
		model.addAttribute("book",bookcatalogService.getBookById(bid));
		model.addAttribute("seller", bookcatalogService.getSeller(bookcatalogService.getBookById(bid).getSeller().getSid()));
		model.addAttribute("index",2);
		model.addAttribute("catalog", "viewbook");
		model.addAttribute("pages", (bookcatalogService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", bookcatalogService.getBooks().subList(0, 4));
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", bookcatalogService.getOrderBookBySellerid(bookcatalogService.getBookById(bid).getSeller().getSid()));
    	model.addAttribute("num",3);
    	model.addAttribute("page", 1);
    			return "seller";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET,params="bid")
	public String deletebook(Model model,@RequestParam int bid) throws Exception{
		
		model.addAttribute("seller", bookcatalogService.getSeller(bookcatalogService.getBookById(bid).getSeller().getSid()));
		model.addAttribute("orderlist", bookcatalogService.getOrderBookBySellerid(bookcatalogService.getBookById(bid).getSeller().getSid()));
		bookcatalogService.deleteBook(bookcatalogService.getBookById(bid));
		model.addAttribute("index",2);
		model.addAttribute("catalog", "catalog");
		model.addAttribute("pages", (bookcatalogService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", bookcatalogService.getBooks().subList(0, 4));
		model.addAttribute("buyerorder", "order");
    	
    	model.addAttribute("num",3);
    	model.addAttribute("page", 1);
		return "seller";
	}
	
	@RequestMapping(value="/updatebook", method=RequestMethod.POST)
	public String updatebook(Model model,Book book,@RequestParam int sid) throws Exception{
		book.setSeller(bookcatalogService.getSeller(sid));
		Book book1=bookcatalogService.getBookById(book.getBid());
		book.setFrondpage(book1.getFrondpage());

		bookcatalogService.updateBook(book);
		model.addAttribute("updatebook", "Updated!");
		model.addAttribute("seller", bookcatalogService.getSeller(sid));
		model.addAttribute("index",2);
		model.addAttribute("catalog", "viewbook");
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", bookcatalogService.getOrderBookBySellerid(sid));
    	model.addAttribute("num",3);
    	model.addAttribute("pages", (bookcatalogService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", bookcatalogService.getBooks().subList(0, 4));
		model.addAttribute("page", 1);
		return "seller";
	}
	

	
	@RequestMapping(value="/upload", method= RequestMethod.POST)
	public String upload(@RequestParam MultipartFile file, Model model,@RequestParam int sid) throws ClassNotFoundException, SQLException, IOException {
		
        
         inputStream = new ByteArrayInputStream(file.getBytes());
         model.addAttribute("seller", bookcatalogService.getSeller(sid));
 		model.addAttribute("catalog", "addbook");
 		model.addAttribute("index",2);
 		model.addAttribute("pages", (bookcatalogService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", bookcatalogService.getBooks().subList(0, 4));
 		model.addAttribute("buyerorder", "order");
     	model.addAttribute("orderlist", bookcatalogService.getOrderBookBySellerid(sid));
     	model.addAttribute("upload", "Upload Successfully!!");
     	model.addAttribute("num",3);
     	model.addAttribute("page", 1);
		return "seller";
		
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/uploadnew", method= RequestMethod.POST)
	public String uploadnew(@RequestParam MultipartFile file, Model model,@RequestParam int sid,@RequestParam int bid) throws Exception {
		
        
         inputStream = new ByteArrayInputStream(file.getBytes());
         Book book=bookcatalogService.getBookById(bid);
         book.setFrondpage(Hibernate.createBlob(inputStream));
         bookcatalogService.updateBook(book);
         model.addAttribute("seller", bookcatalogService.getSeller(sid));
 		model.addAttribute("catalog", "viewbook");
 		model.addAttribute("index",2);
 		model.addAttribute("pages", (bookcatalogService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", bookcatalogService.getBooks().subList(0, 4));
 		model.addAttribute("buyerorder", "order");
     	model.addAttribute("orderlist", bookcatalogService.getOrderBookBySellerid(sid));
     	model.addAttribute("book", book);
     	model.addAttribute("num",3);
     	model.addAttribute("page", 1);
		return "seller";
		
	}

	

}
