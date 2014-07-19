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
public class Cart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cid;
	
	@OneToMany(mappedBy="cart",cascade=CascadeType.ALL)
	private List<OrderBook> orderbooks;
	
	@OneToOne
	private Buyer buyer;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public List<OrderBook> getOrderbooks() {
		return orderbooks;
	}

	public void setOrderbooks(List<OrderBook> orderbooks) {
		this.orderbooks = orderbooks;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
	

}
