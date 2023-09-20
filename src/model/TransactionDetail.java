package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;

public class TransactionDetail {
	
	private Integer transaction_id;
	private Integer food_id;
	private Integer quantity;
	private Integer total_price;
	
	public TransactionDetail(Integer transaction_id, Integer food_id, Integer quantity, Integer total_price) {
		super();
		this.transaction_id = transaction_id;
		this.food_id = food_id;
		this.quantity = quantity;
		this.total_price = total_price;
	}

	public Integer getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Integer transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Integer getFood_id() {
		return food_id;
	}

	public void setFood_id(Integer food_id) {
		this.food_id = food_id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Integer total_price) {
		this.total_price = total_price;
	}
	
	public String getFoodName() {
		Connect connect = Connect.getConnection();
		String query = String.format("select name from food where id = '%d'", food_id);
		ResultSet rs = connect.executeQuery(query);
		
			String foodName = "";
		try {
			if(rs.next()) {
				foodName = rs.getString("name");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return foodName;
	}
	
	public int getFoodPrice() {
		Connect connect = Connect.getConnection();
		String query = String.format("select price from food where id = '%d'", food_id);
		ResultSet rs = connect.executeQuery(query);
		
			int foodPrice = 0;
		try {
			if(rs.next()) {
				foodPrice = rs.getInt("price");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return foodPrice;
	}
	
	public String getFoodType() {
		Connect connect = Connect.getConnection();
		String query = String.format("select type from food where id = '%d'", food_id);
		ResultSet rs = connect.executeQuery(query);
		
			String foodType = "";
		try {
			if(rs.next()) {
				foodType = rs.getString("type");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return foodType;
	}
	

}
