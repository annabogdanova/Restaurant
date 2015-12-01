package com.epam.restaurant.server;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.epam.restaurant.client.RestaurantService;
import com.epam.restaurant.shared.Bill;
import com.epam.restaurant.shared.BillMapper;
import com.epam.restaurant.shared.Dish;
import com.epam.restaurant.shared.DishMapper;
import com.epam.restaurant.shared.Order;
import com.epam.restaurant.shared.OrderMapper;
import com.epam.restaurant.shared.tmpForBill;
import com.epam.restaurant.shared.tmpForOrder;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RestaurantServiceImpl extends RemoteServiceServlet implements
		RestaurantService {

	static Logger logger = Logger.getLogger("RestaurantLogger");
		
	
	private static SqlSessionFactory sqlSessionFactory;
	
	@Override
	public List<Dish> getMenu() {
		try {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(
					Resources.getResourceAsReader("com/epam/restaurant/server/config.xml"));
			sqlSessionFactory.getConfiguration().addMapper(DishMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			DishMapper mapper = session.getMapper(DishMapper.class);
			List<Dish> menu = mapper.getDish();
			logger.log(Level.SEVERE, "getMenu dish = " + menu.get(0).getDish());
			return menu;
		}
	}

	@Override
	public void saveOrder(List<Integer> dishesId, String client) {
		try {
			Reader resourceReader = Resources.getResourceAsReader("com/epam/restaurant/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(
					resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(OrderMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			tmpForOrder obj = new tmpForOrder();
				obj.setClient(client);
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			for (int id : dishesId) {
				obj.setId(id);
				mapper.saveOrder(obj);
				logger.log(Level.SEVERE, Integer.toString(id));
			}
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public List<Order> getOrders(String client) {
		try {
			Reader resourceReader = Resources.getResourceAsReader("com/epam/restaurant/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(
					resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(OrderMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			return mapper.getOrderForUser(client);
		} finally {
			session.close();
		}
	}

	@Override
	public void confirmOrder(String client, int amount) {
		try {
			Reader resourceReader = Resources.getResourceAsReader("com/epam/restaurant/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(
					resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(OrderMapper.class);
			sqlSessionFactory.getConfiguration().addMapper(BillMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.getMapper(OrderMapper.class).confirmOrderForUser(client);
			BillMapper billMapper = session.getMapper(BillMapper.class);
			tmpForBill obj = new tmpForBill();
			obj.setClient(client);
			obj.setAmount(amount);
			logger.log(Level.SEVERE, "obj " + obj.getAmount() + ", " + obj.getClient());
			billMapper.saveBill(obj);
			session.commit();
		} finally {
			session.close();
		}
		
	}

	@Override
	public Bill getBill(String clientName) {
		try {
			Reader resourceReader = Resources.getResourceAsReader("com/epam/restaurant/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(
					resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(BillMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			BillMapper mapper = session.getMapper(BillMapper.class);
			logger.log(Level.SEVERE, "got mapper");
			return mapper.getBill(clientName);
		} finally {
			session.close();
		}
	}

	@Override
	public void payBill(String clientName) {
		try {
			Reader resourceReader = Resources.getResourceAsReader("com/epam/restaurant/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(
					resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(BillMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			BillMapper mapper = session.getMapper(BillMapper.class);
			mapper.payBill(clientName);
			session.commit();
		} finally {
			session.close();
		}		
	}
}
