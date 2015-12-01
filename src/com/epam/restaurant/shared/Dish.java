package com.epam.restaurant.shared;

import java.io.Serializable;

public class Dish implements Serializable{

	String id;
	String dish;
	String price;
	
	public String getId () {
		return id;
	}
	
	public void setId (String id) {
		this.id = id;
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
	
}
