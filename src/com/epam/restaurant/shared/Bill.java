package com.epam.restaurant.shared;

import java.io.Serializable;

public class Bill implements Serializable{

	String id;
	String client;
	String amount;
	int paid;
	
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
	
	public String getAmount () {
		return amount;
	}
	
	public void setAmount (String amount) {
		this.amount = amount;
	}
	
	public int getDone () {
		return paid;
	}
	
	public void setDone (int paid) {
		this.paid = paid;
	}
	
}
