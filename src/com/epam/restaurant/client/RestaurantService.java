package com.epam.restaurant.client;

import java.util.List;

import com.epam.restaurant.shared.Bill;
import com.epam.restaurant.shared.Dish;
import com.epam.restaurant.shared.Order;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("restaurant")
public interface RestaurantService extends RemoteService {

	List<Dish> getMenu();

	void saveOrder(List<Integer> dishesId, String client);

	List<Order> getOrders(String client);

	void confirmOrder(String client, int amount);

	Bill getBill(String clientName);

	void payBill(String clientName);
}
