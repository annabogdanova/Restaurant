package com.epam.restaurant.shared;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper {

	@Select ("SELECT * FROM restaurantDB.orders")
	List<Order> getOrder();
	
	@Insert ("INSERT INTO restaurantDB.orders (client, dish, price, done, dishId)" +
			"VALUES (#{client}, (SELECT restaurantDB.menu.dish FROM restaurantDB.menu WHERE restaurantDB.menu.id = #{id})," +
			"(SELECT restaurantDB.menu.price FROM restaurantDB.menu WHERE restaurantDB.menu.id = #{id})," +
			"0, (SELECT restaurantDB.menu.id FROM restaurantDB.menu WHERE restaurantDB.menu.id = #{id}));")
	void saveOrder(tmpForOrder obj);

	@Select ("SELECT * FROM restaurantDB.orders WHERE restaurantDB.orders.client = #{client} AND restaurantDB.orders.done = 0")
	List<Order> getOrderForUser(String client);
				
	@Update ("UPDATE restaurantDB.orders SET restaurantDB.orders.done = 1 WHERE restaurantDB.orders.client = #{client}")
	void confirmOrderForUser(String client);
	
	
}
