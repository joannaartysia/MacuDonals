package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;

public class TransactionHeader {
	
	private Integer transaction_id;
	private Integer user_id;
	private String date;
	
	public TransactionHeader(Integer transaction_id, Integer user_id, String date) {
		super();
		this.transaction_id = transaction_id;
		this.user_id = user_id;
		this.date = date;
	}

	public Integer getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Integer transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getUsernameTH() {
		Connect connect = Connect.getConnection();
		String query = String.format("select username from user where id = '%d'", user_id);
		ResultSet rs = connect.executeQuery(query);
		
			String usernameTH = "";
		try {
			if(rs.next()) {
				usernameTH = rs.getString("username");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return usernameTH;
	}
	
	public String getRoleTH() {
		Connect connect = Connect.getConnection();
		String query = String.format("select role from user where id = '%d'", user_id);
		ResultSet rs = connect.executeQuery(query);
		
			String roleTH = "";
		try {
			if(rs.next()) {
				roleTH = rs.getString("role");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return roleTH;
	}
	

}
