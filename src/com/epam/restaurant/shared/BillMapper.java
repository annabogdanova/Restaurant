package com.epam.restaurant.shared;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface BillMapper {

	@Select ("SELECT * FROM restaurantDB.bills WHERE restaurantDB.bills.client = #{client} AND restaurantDB.bills.paid = 0")
	Bill getBill(String client);
	
	@Update ("UPDATE restaurantDB.bills SET restaurantDB.bills.paid = 1 WHERE restaurantDB.bills.client = #{client}")
	void payBill(String client);

	@Insert ("INSERT INTO restaurantDB.bills (client, amount, paid) VALUES (#{client}, #{amount}, 0)")
	void saveBill(tmpForBill obj);

	
}
