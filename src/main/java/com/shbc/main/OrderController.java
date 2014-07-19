package com.shbc.main;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.OrderService;

import beans.Address;
import beans.Orders;


@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	

	
	@RequestMapping(value="/addtocart", method=RequestMethod.GET)
	public String addtocart(Model model,@RequestParam int bkid,@RequestParam int bid) throws Exception{
		orderService.addToCart(bkid, bid);
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", orderService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("buyer", orderService.getBuyer(bid));
	  	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 0);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "buyer";
	}
	
	@RequestMapping(value="/quantity", method=RequestMethod.GET)
	private String modified(Model model,@RequestParam int bid,@RequestParam int obid,@RequestParam String method) throws Exception{
		orderService.modifiedQuantity(bid, obid, method);
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", orderService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("buyer", orderService.getBuyer(bid));
	  	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 1);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return"buyer";
		
	}
	@RequestMapping(value="/checkout", method=RequestMethod.GET, params= "bid")
	private String checkout(Model model,@RequestParam int bid) throws Exception{
		
		model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		
		model.addAttribute("shopping", "checkout");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", orderService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("buyer", orderService.getBuyer(bid));
		model.addAttribute("index", 1);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "buyer";
		
	}
	
	@RequestMapping(value="checkout", method=RequestMethod.POST)
	private String finalcheckout(Model model,Address address,@RequestParam int bid) throws Exception{
		orderService.checkout(address, bid);
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", orderService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("buyer", orderService.getBuyer(bid));
	  	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 2);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "buyer";
		
	}
	@RequestMapping(value="/backshopping", method=RequestMethod.GET, params= "bid")
	private String backshopping(Model model,@RequestParam int bid) throws Exception{
		
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", orderService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("buyer", orderService.getBuyer(bid));
	  	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 1);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "buyer";
		
	}
	
	@RequestMapping(value="/orderdetail", method=RequestMethod.GET)
	private String orderdetail(Model model,@RequestParam int bid,@RequestParam int oid) throws Exception{
		
		model.addAttribute("oneorder", orderService.getOrder(oid));
		
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "detail");
	  	model.addAttribute("orderlist", orderService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("buyer", orderService.getBuyer(bid));
	  	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 2);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "buyer";
		
	}
	
	@RequestMapping(value="/backorder", method=RequestMethod.GET, params= "bid")
	private String backorder(Model model,@RequestParam int bid) throws Exception{
		
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", orderService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("buyer", orderService.getBuyer(bid));
	  	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 2);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "buyer";
		
	}
	
	@RequestMapping(value="/cart", method=RequestMethod.GET, params= "bid")
	private String cart(Model model,@RequestParam int bid) throws Exception{
		
		model.addAttribute("shopping", "shopping");
	  	model.addAttribute("buyerorder", "order");
	  	model.addAttribute("orderlist", orderService.getOrderBookByBuyerId(bid));
	  	model.addAttribute("buyer", orderService.getBuyer(bid));
	  	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 1);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "buyer";
		
	}
	
	@RequestMapping(value="/orderdetails", method=RequestMethod.GET)
	private String orderdetails(Model model,@RequestParam int sid,@RequestParam int oid) throws Exception{
		
		model.addAttribute("oneorder", orderService.getOrder(oid));
		
		model.addAttribute("seller", orderService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
    	model.addAttribute("orderlist", orderService.getOrderBookBySellerid(sid));
    	model.addAttribute("buyerorder", "detail");
    	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 1);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "seller";
		
	}
	
	@RequestMapping(value="/backorders", method=RequestMethod.GET, params= "sid")
	private String backorders(Model model,@RequestParam int sid) throws Exception{
		
		model.addAttribute("seller", orderService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
    	model.addAttribute("orderlist", orderService.getOrderBookBySellerid(sid));
    	model.addAttribute("buyerorder", "order");
    	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 1);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "seller";
		
	}

	@RequestMapping(value="/updateorder", method=RequestMethod.POST)
	private String updateorder(Model model,@RequestParam int sid,Orders oneorder) throws Exception{
		orderService.updateOrder(sid, oneorder);
		model.addAttribute("oneorder", orderService.getOrder(oneorder.getOid()));
		model.addAttribute("seller", orderService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
    	model.addAttribute("orderlist", orderService.getOrderBookBySellerid(sid));
    	model.addAttribute("buyerorder", "detail");
    	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 1);
		model.addAttribute("updateorders", "Update Successfully!!");
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "seller";
		
	}
	
	@RequestMapping(value="/receviedorder", method=RequestMethod.GET, params= "sid")
	private String receviedorder(Model model,@RequestParam int sid) throws Exception{
		
		model.addAttribute("seller", orderService.getSeller(sid));
		model.addAttribute("catalog", "catalog");
    	model.addAttribute("orderlist", orderService.getOrderBookBySellerid(sid));
    	model.addAttribute("buyerorder", "order");
    	model.addAttribute("pages", (orderService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", orderService.getBooks().subList(0, 4));
		model.addAttribute("index", 1);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return "seller";
		
	}
	

}
