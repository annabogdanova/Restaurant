package com.epam.restaurant.client;

import java.util.List;

import com.epam.restaurant.shared.Bill;
import com.epam.restaurant.shared.Dish;
import com.epam.restaurant.shared.Order;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RestaurantServiceAsync {

	void getMenu(AsyncCallback<List<Dish>> asyncCallback);

	void saveOrder(List<Integer> dishesId, String client, AsyncCallback<Void> callback);

	void getOrders(String client, AsyncCallback<List<Order>> asyncCallback);

	void confirmOrder(String client, int amount, AsyncCallback<Void> asyncCallback);

	void getBill(String clientName, AsyncCallback<Bill> asyncCallback);

	void payBill(String clientName, AsyncCallback<Void> asyncCallback);
}
