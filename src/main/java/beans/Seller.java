package beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Seller{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sid;
	private String company;
	private String website;
	private String description;
	
	@OneToOne
	private User user;
	
	@OneToMany(mappedBy="seller",cascade=CascadeType.ALL)
	private List<Book> bookcatalog;
	
	@OneToMany(mappedBy="seller",cascade=CascadeType.ALL)
	private List<Orders> orders;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Book> getBookcatalog() {
		return bookcatalog;
	}

	public void setBookcatalog(List<Book> bookcatalog) {
		this.bookcatalog = bookcatalog;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	
	
	
	

}
