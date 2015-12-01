package com.epam.restaurant.shared;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface DishMapper {

	@Select ("SELECT * FROM restaurantDB.menu")
	List<Dish> getDish();
	
}
