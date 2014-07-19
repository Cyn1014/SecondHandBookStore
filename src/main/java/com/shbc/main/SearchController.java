package com.shbc.main;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import service.SearchService;


import beans.Book;

import beans.Seller;


@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	
	@RequestMapping(value="/searchs")
	public String searchs(Model model,@RequestParam String word,@RequestParam String type,@RequestParam int sid,@RequestParam int page){
		List<Book> books=null;
		if(word.equalsIgnoreCase("null"))
		books=searchService.getBooks();
		else{
		model.addAttribute("word", word);
		word="%"+word+"%";
		books=searchService.getBookByWord(word, type);
		}
		model.addAttribute("type", type);
		int pages=(books.size()+3)/4;
		model.addAttribute("bookcatalog", books.subList((page-1)*4, Math.min((page*4),books.size())));
        model.addAttribute("pages", pages);
		model.addAttribute("index",0);
		model.addAttribute("seller", searchService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
		model.addAttribute("search", "search");
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", searchService.getOrderBookBySellerid(sid));
    	model.addAttribute("num",3);
    	model.addAttribute("page", page);
		return "seller";
	}
	
	@RequestMapping(value="/backsearchs",method=RequestMethod.GET,params="sid")
	public String backsearchs(Model model,@RequestParam int sid){
		model.addAttribute("seller", searchService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
		model.addAttribute("pages", (searchService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", searchService.getBooks().subList(0, 4));
		model.addAttribute("index", 0);
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", searchService.getOrderBookBySellerid(sid));
    	model.addAttribute("num",3);
    	model.addAttribute("page", 1);
		return "seller";
	}
	
	@RequestMapping(value="/viewbooks", method=RequestMethod.GET)
	public String viewbookm(Model model,@RequestParam int bid,@RequestParam int sid){
		List<Book> books=searchService.getRelatedBook(bid);
		int num=Math.max(3, books.size());
		model.addAttribute("num",num);
		model.addAttribute("book",searchService.getBookById(bid));
		model.addAttribute("relatedbook",books);
		model.addAttribute("seller", searchService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
		model.addAttribute("index",0);
		model.addAttribute("search", "detail");
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", searchService.getOrderBookBySellerid(sid));
		return "seller";
	}
	
	@RequestMapping(value="/sellerInfos", method=RequestMethod.GET)
	public String sellerInfos(Model model,@RequestParam int bid,@RequestParam int sid) throws IOException, Exception{
		model.addAttribute("seller", searchService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
		model.addAttribute("book",searchService.getBookById(bid));
		model.addAttribute("index",0);
		model.addAttribute("search", "sellerInfo");
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", searchService.getOrderBookBySellerid(sid));

		Seller seller=searchService.getSeller(searchService.getBookById(bid).getSeller().getSid());
		model.addAttribute("seller1",seller);
		if(seller.getCompany().length()>0){
		model.addAttribute("feed", searchService.getNews(seller));
		model.addAttribute("map", searchService.getMapUrl(seller));
		}
		model.addAttribute("num",3);
		return "seller";
	}
	
	@RequestMapping(value="/backviews",method=RequestMethod.GET)
	public String backviews(Model model,@RequestParam int bid, @RequestParam int sid){
	
		List<Book> books=searchService.getRelatedBook(bid);
		int num=Math.max(3, books.size());
		model.addAttribute("num",num);
		model.addAttribute("book",searchService.getBookById(bid));
		model.addAttribute("relatedbook",books);
		model.addAttribute("seller", searchService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
		model.addAttribute("index",0);
		model.addAttribute("search", "detail");
		model.addAttribute("buyerorder", "order");
    	model.addAttribute("orderlist", searchService.getOrderBookBySellerid(sid));
    	model.addAttribute("pages", 1);
		return "seller";
	}
	@RequestMapping(value="/searchm")
	public String searchm(Model model,@RequestParam String word,@RequestParam String type, @RequestParam int page){
		
		List<Book> books=null;
		if(word.equalsIgnoreCase("null"))
		books=searchService.getBooks();
		else{
		model.addAttribute("word", word);
		word="%"+word+"%";
		books=searchService.getBookByWord(word, type);
		}
		model.addAttribute("type", type);
		int pages=(books.size()+3)/4;
		model.addAttribute("bookcatalog", books.subList((page-1)*4, Math.min((page*4),books.size())));
		model.addAttribute("index",2);
		model.addAttribute("search", "search");
		model.addAttribute("num",3);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		return "mainscreen";
	}
	
	@RequestMapping(value="/backsearchm",method=RequestMethod.GET)
	public String backsearchm(Model model){
		model.addAttribute("pages", (searchService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", searchService.getBooks().subList(0, 4));
		model.addAttribute("index", 2);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "mainscreen";
	}
	
	@RequestMapping(value="/viewbookm", method=RequestMethod.GET,params="bid")
	public String viewbookm(Model model,@RequestParam int bid){
		List<Book> books=searchService.getRelatedBook(bid);
		int num=Math.max(3, books.size());
		model.addAttribute("num",num);
		model.addAttribute("book",searchService.getBookById(bid));
		model.addAttribute("relatedbook",books);
		model.addAttribute("index",2);
		model.addAttribute("search", "detail");
		
		return "mainscreen";
	}
	
	@RequestMapping(value="/sellerInfom", method=RequestMethod.GET,params="bid")
	public String sellerInfom(Model model,@RequestParam int bid) throws IOException, Exception{
		model.addAttribute("book",searchService.getBookById(bid));
		model.addAttribute("index",2);
		model.addAttribute("search", "sellerInfo");
		Seller seller=searchService.getSeller(searchService.getBookById(bid).getSeller().getSid());
		model.addAttribute("seller",seller);
		if(seller.getCompany().length()>0){
		
			model.addAttribute("feed", searchService.getNews(seller));
			model.addAttribute("map", searchService.getMapUrl(seller));
		
		}
		model.addAttribute("num",3);
		
		return "mainscreen";
	}
	
	@RequestMapping(value="/backviewm",method=RequestMethod.GET,params="bid")
	public String backviewm(Model model,@RequestParam int bid){
		List<Book> books=searchService.getRelatedBook(bid);
		int num=Math.max(3, books.size());
		model.addAttribute("num",num);
		model.addAttribute("book",searchService.getBookById(bid));
		model.addAttribute("relatedbook",books);
		model.addAttribute("index",2);
		model.addAttribute("search", "detail");
		return "mainscreen";
	}
	
	@RequestMapping(value="/searchb")
	public String searchb(Model model,@RequestParam String word,@RequestParam String type,@RequestParam int bid, @RequestParam int page){
		List<Book> books=null;
		if(word.equalsIgnoreCase("null"))
		books=searchService.getBooks();
		else{
		model.addAttribute("word", word);
		word="%"+word+"%";
		books=searchService.getBookByWord(word, type);
		}
		model.addAttribute("type", type);
		int pages=(books.size()+3)/4;
		model.addAttribute("bookcatalog", books.subList((page-1)*4, Math.min((page*4),books.size())));
		model.addAttribute("buyer", searchService.getBuyer(bid));
		model.addAttribute("index",0);
		model.addAttribute("search", "search");
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", searchService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("num",3);
	  	model.addAttribute("pages", pages);
	  	model.addAttribute("page", page);
		return "buyer";
	}
	
	@RequestMapping(value="/backsearchb",method=RequestMethod.GET)
	public String backsearchb(Model model,@RequestParam int bid){
		model.addAttribute("buyer", searchService.getBuyer(bid));
		model.addAttribute("pages", (searchService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", searchService.getBooks().subList(0, 4));
		model.addAttribute("index", 0);
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", searchService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("num",3);
	  	model.addAttribute("page", 1);
		
		return "buyer";
	}
	
	@RequestMapping(value="/viewbookb", method=RequestMethod.GET)
	public String viewbookb(Model model,@RequestParam int bkid,@RequestParam int bid){
		List<Book> books=searchService.getRelatedBook(bkid);
		int num=Math.max(3, books.size());
		model.addAttribute("num",num);
		model.addAttribute("buyer", searchService.getBuyer(bid));
		model.addAttribute("book",searchService.getBookById(bkid));
		model.addAttribute("relatedbook",books);
		model.addAttribute("index",0);
		model.addAttribute("search", "detail");
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", searchService.getOrderBookByBuyerId(bid));
	  
		return "buyer";
	}
	
	@RequestMapping(value="/sellerInfob", method=RequestMethod.GET)
	public String sellerInfob(Model model,@RequestParam int bid,@RequestParam int bkid) throws IOException, Exception{
		model.addAttribute("book",searchService.getBookById(bkid));
		model.addAttribute("buyer", searchService.getBuyer(bid));
		model.addAttribute("index",0);
		model.addAttribute("search", "sellerInfo");
		Seller seller=searchService.getSeller(searchService.getBookById(bkid).getSeller().getSid());
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", searchService.getOrderBookByBuyerId(bid));
	  	
		model.addAttribute("seller",seller);
		if(seller.getCompany().length()>0){
			model.addAttribute("feed", searchService.getNews(seller));
			model.addAttribute("map", searchService.getMapUrl(seller));
		}
		model.addAttribute("num",3);
	
		return "buyer";
	}
	
	@RequestMapping(value="/backviewb",method=RequestMethod.GET)
	public String backviewb(Model model,@RequestParam int bid,@RequestParam int bkid){
		List<Book> books=searchService.getRelatedBook(bkid);
		int num=Math.max(3, books.size());
		model.addAttribute("num",num);
		model.addAttribute("book",searchService.getBookById(bkid));
		model.addAttribute("relatedbook",books);
		model.addAttribute("buyer", searchService.getBuyer(bid));
		model.addAttribute("index",0);
		model.addAttribute("search", "detail");
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", searchService.getOrderBookByBuyerId(bid));
	  	
		return "buyer";
	}
	

}
