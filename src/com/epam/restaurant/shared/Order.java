package com.epam.restaurant.shared;

import java.io.Serializable;

public class Order implements Serializable{

	String id;
	String client;
	String dish;
	String price;
	int done;
	
	public String getId () {
		return id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getClient () {
		return client;
	}
	
	public void setClient (String client) {
		this.client = client;
	}
	
	public String getDish () {
		return dish;
	}
	
	public void setDish (String dish) {
		this.dish = dish;
	}
	
	public String getPrice () {
		return price;
	}
	
	public void setPrice (String price) {
		this.price = price;
	}
	
	public int getDone () {
		return done;
	}
	
	public void setDone (int done) {
		this.done = done;
	}
	
}
