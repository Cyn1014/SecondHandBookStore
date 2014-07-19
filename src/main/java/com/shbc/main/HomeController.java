package com.shbc.main;



import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import service.HomeService;




import beans.*;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main( Model model) {
		
		model.addAttribute("bookcatalog", homeService.getBooks().subList(0, 4));
		model.addAttribute("search", "search");
		model.addAttribute("index", 0);
		model.addAttribute("num",3);
		model.addAttribute("pages", (homeService.getBooks().size()+3)/4);
		model.addAttribute("page", 1);
		return "mainscreen";
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(User user,Buyer buyer,Seller seller,Model model,Address address,@RequestParam String rpassword) {
		int index=0;
		String registerror=homeService.validate(user, rpassword);
		if(registerror!=null){
			index=1;
			model.addAttribute("user", user);
			model.addAttribute("seller", seller);
			model.addAttribute("address", address);
		}else{ 
			homeService.saveUser(user, address, buyer, seller);
			model.addAttribute("message", "User Account has been created!!");
			}
		model.addAttribute("pages", (homeService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", homeService.getBooks().subList(0, 4));
		model.addAttribute("page", 1);
		model.addAttribute("registerror", registerror);
		model.addAttribute("num",3);
		model.addAttribute("index", index);
		return "mainscreen";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login( Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String page="home";
	      User user=homeService.getUserByName(auth.getName());
	      String role=user.getRole();
	      if(role.equalsIgnoreCase("buyer")){
	    	  page="buyer";
	    	  model.addAttribute("shopping", "shopping");
	    	  model.addAttribute("buyerorder", "order");
	    	  model.addAttribute("orderlist", homeService.getOrderBookByBuyerId(homeService.getBuyerUser(user.getUid()).getBid()));
	    	  model.addAttribute("buyer", homeService.getBuyerUser(user.getUid()));
	      }
	      else if(role.equalsIgnoreCase("seller")){
	    	  page="seller";
	    	  model.addAttribute("buyerorder", "order");
	    	  model.addAttribute("orderlist", homeService.getOrderBookBySellerid(homeService.getSellerUser(user.getUid()).getSid()));
	    	  model.addAttribute("seller", homeService.getSellerUser(user.getUid()));
	    	  model.addAttribute("catalog", "catalog");
	      }
	      model.addAttribute("index", 0);
	      model.addAttribute("oneorder", null);
	      model.addAttribute("pages", (homeService.getBooks().size()+3)/4);
			model.addAttribute("search", "search");
			model.addAttribute("bookcatalog", homeService.getBooks().subList(0, 4));
			model.addAttribute("num",3);
			model.addAttribute("page", 1);
		  return page;
	}
	
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String editProfile(User user,Buyer buyer,Seller seller,Model model,Address address,@RequestParam String rpassword) throws Throwable {

		
		String editerror=homeService.validate(user, rpassword);
		String updatesuccess=null;
		String page=null;
		if(editerror==null){
			homeService.updateUser(user, address, buyer, seller);
			updatesuccess="Update Successfully!!!";
		}
		if(user.getRole().equalsIgnoreCase("buyer"))
			{
			page="buyer";
		    model.addAttribute("shopping", "shopping");
  	        model.addAttribute("buyerorder", "order");
  	        model.addAttribute("orderlist", homeService.getOrderBookByBuyerId((homeService.getBuyerUser(user.getUid()).getBid())));
  	        model.addAttribute("buyer",homeService.getBuyerUser(user.getUid()));
		}else if(user.getRole().equalsIgnoreCase("seller"))
			{
			page="seller";
			
			model.addAttribute("buyerorder", "order");
	    	model.addAttribute("orderlist", homeService.getOrderBookBySellerid(seller.getSid()));
	    	model.addAttribute("seller", homeService.getSeller(seller.getSid()));
			model.addAttribute("catalog", "catalog");
			}
		model.addAttribute("pages", (homeService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", homeService.getBooks().subList(0, 4));
		model.addAttribute("index",3);
		model.addAttribute("updatesuccess", updatesuccess);
		model.addAttribute("editerror", editerror);
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return page;
	}
	
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginfailed( Model model) {
		  String loginerror="The username and password pair does not match!";
		  
		  model.addAttribute("pages", (homeService.getBooks().size()+3)/4);
			model.addAttribute("search", "search");
			model.addAttribute("bookcatalog", homeService.getBooks().subList(0, 4));
	      model.addAttribute("loginerror", loginerror);
	      model.addAttribute("index", 0);
	      model.addAttribute("num",3);
	      model.addAttribute("page", 1);
	     		  return "mainscreen";
	}

	
	
	@RequestMapping(value="/edit", method=RequestMethod.GET,params="uid")
	public String edit(Model model,Book book,@RequestParam int uid){
		User user=homeService.getUser(uid);
		String page="home";
		if(user.getRole().equalsIgnoreCase("seller"))
		{
			page="seller";
			
	    	model.addAttribute("orderlist", homeService.getOrderBookBySellerid(homeService.getSellerUser(uid).getSid()));

			model.addAttribute("seller", homeService.getSellerUser(uid));
		}else if(user.getRole().equalsIgnoreCase("buyer")){
			page="buyer";
			 model.addAttribute("shopping", "shopping");
			model.addAttribute("buyer", homeService.getBuyerUser(uid));
			model.addAttribute("orderlist", homeService.getOrderBookByBuyerId((homeService.getBuyerUser(uid).getBid())));
		}
		model.addAttribute("buyerorder", "order");
		model.addAttribute("index",3);
		model.addAttribute("catalog", "catalog");
		model.addAttribute("pages", (homeService.getBooks().size()+3)/4);
		model.addAttribute("search", "search");
		model.addAttribute("bookcatalog", homeService.getBooks().subList(0, 4));
		model.addAttribute("num",3);
		model.addAttribute("page", 1);
		return page;
	}
	
	
	
	@RequestMapping(value="/image",method=RequestMethod.GET,params="bid")
	public @ResponseBody void image(@RequestParam int bid,HttpServletResponse response) throws IOException, SQLException{
		java.sql.Blob blob=homeService.getBook(bid).getFrondpage();
		
		OutputStream out=response.getOutputStream();
		out.write(blob.getBytes(1, (int)blob.length()));
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/validate",params="username")
	public @ResponseBody String validate(@RequestParam String username){
		String exit="false";
		User user=homeService.getUserByName(username);
		if(user!=null)exit="true";
		
		return exit;
		
	}

	@RequestMapping(value="/sendEmail",method=RequestMethod.POST)
	public String sendEmail(Model model,@RequestParam String email,@RequestParam String subject,@RequestParam String message){
		homeService.sendMail(subject, email, message);
		model.addAttribute("bookcatalog", homeService.getBooks().subList(0, 4));
		model.addAttribute("search", "search");
		model.addAttribute("index", 3);
		model.addAttribute("num",3);
		model.addAttribute("pages", (homeService.getBooks().size()+3)/4);
		model.addAttribute("page", 1);
		return "mainscreen";
	}

	
	
	
}
