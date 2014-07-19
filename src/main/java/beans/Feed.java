package beans;

import java.util.ArrayList;

public class Feed {
	private ArrayList<Item> items=new ArrayList<Item>();
	private String title;
	private String description;
	
	public Feed(){
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Item addItem(){
		Item item=new Item();
		items.add(item);
		return item;
	}

}
