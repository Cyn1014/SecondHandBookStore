package beans;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int oid;
	
	@OneToMany(mappedBy="order")
	private List<OrderBook> books;
	
	@ManyToOne
	@JoinColumn(name="seller_id")
	private Seller seller;
	
	@ManyToOne
	@JoinColumn(name="buyer_id")
	private Buyer buyer;
	
	@OneToOne
	private Address address;
	
	private String tracking;
	private boolean isreturn;
	private String status;

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public List<OrderBook> getBooks() {
		return books;
	}

	public void setBooks(List<OrderBook> books) {
		this.books = books;
	}

	public String getTracking() {
		return tracking;
	}

	public void setTracking(String tracking) {
		this.tracking = tracking;
	}

	public boolean isIsreturn() {
		return isreturn;
	}

	public void setIsreturn(boolean isreturn) {
		this.isreturn = isreturn;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	

}
