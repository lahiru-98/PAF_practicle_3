package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.xdevapi.PreparableStatement;

public class Item {

	
	private int itemID;
	private String itemCode;
	private String itemName;
	private double itemPrice;
	private String itemDescription;
	//default constructor
	public Item() {
		
	}
	
	//constructor
	public Item(int itemID, String itemCode, String itemName, double itemPrice, String itemDescription) {
		super();
		this.itemID = itemID;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemDescription = itemDescription;
	}

	//getters 
	
	
	
	public int getItemID() {
		return itemID;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public String getItemDescription() {
		return itemDescription;
	}


	@Override
	public String toString() {
		return "Item [itemID=" + itemID + ", itemCode=" + itemCode + ", itemName=" + itemName + ", itemPrice="
				+ itemPrice + ", itemDescription=" + itemDescription + "]";
	}

	//-------------------------------------------------------------------------------------------
	private static final String URL = "jdbc:mysql://localhost:3306/paf_lab_3";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	// method to get the connection
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// testing
			//System.out.println("Sucessfully connected !!!");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return con;
	}

	// method to insert a Item
	public String insertItem(String code, String name, String price, String desc) {

		String returnMsg = "";
		try {

			Connection conn = connect();
			if (conn == null) {
				returnMsg = " Error while Connecting to the database";

			}

			String query = " insert into items (`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"
			+ " values (?, ?, ?, ?, ?)";
					

			PreparedStatement preparedstatement = conn.prepareStatement(query);

			// binding values
			preparedstatement.setInt(1, 0);
			preparedstatement.setString(2, code);
			preparedstatement.setString(3, name);
			preparedstatement.setDouble(4, Double.parseDouble(price));
			preparedstatement.setString(5, desc);

			preparedstatement.execute();
			conn.close();

			returnMsg = "Inserted SuccessFully";

		} catch (SQLException e) {

			returnMsg = "Error while Inserting ";
			e.printStackTrace();
		}

		return returnMsg;
	}


	public String readItems() {
		String output = "";

		try {

			Connection con = connect();
			if (con == null) {
				output = " Error while connecting to the DataBase";
			}

			output = "<table border='1'>" + "<tr><th>Item Code</th><th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th><th>Update</th><th>Remove</th></tr>";  //table header 
			
			String query = "select * from items";
			Statement stmt = con.createStatement();
			
			//getting the result to the result set
			ResultSet resultSet = stmt.executeQuery(query);
			
			//iterating through the result set and preparing the table
			while(resultSet.next()) {
				
				//read a row and storing them on our variables 
				String itemID = Integer.toString(resultSet.getInt("itemID"));
				String itemCode = resultSet.getString("itemCode");
				String itemName = resultSet.getString("itemName");
				String itemPrice = Double.toString(resultSet.getDouble("itemPrice"));
				String itemDecscription = resultSet.getString("itemDesc");
				
				//adding a new raw to the html table
				output += "<tr><td>" +itemCode+ "</td> ";
				output += "<td>" +itemName+ "</td>";
				output += "<td>" +itemPrice+ "</td>";
				output += "<td>" +itemDecscription+ "</td>";
				
				//adding two buttons
				output += "<td><form method='post' action='items.jsp' >"
						+ "<input name='btnupdate' type='submit'  class=\"btn btn-primary\" value='update'> "
						+ "<input name='updateId' type='hidden' value='" + itemID +"'>"
						+ "</form></td>";
				
				output += "<td> <form method='post' action='items.jsp'>"
						+ "<input name='btnremove' type='submit' class='btn btn-danger' value='Remove'>"
						+ "<input name='itemId' type='hidden' value='" + itemID + "'>" 
								+ "</form></td></tr>";
				
			}//end of while
			
			//closing the connection
			con.close();
			//complete the html 
			output +="</table>";
			

		} catch (Exception e) {
			output = " Error while reading the Items";
		}
		return output;
	}

	//method to read an spcific item 
	public Item readOneItem(String id) {
		
		Item returnItem = null;
		
		try {
			Connection con = connect();
			if(con==null) {
				System.out.println("Error While Connecting to the DB - inside readOneItem()");
			}
			
			String query = " select * from items where itemID = "+id ;
			PreparedStatement stmt = con.prepareStatement(query);
			
			ResultSet resultSet = stmt.executeQuery();
			
			while(resultSet.next()) {
				
				String itemID = Integer.toString(resultSet.getInt("itemID"));
				String itemCode = resultSet.getString("itemCode");
				String itemName = resultSet.getString("itemName");
				String itemPrice = Double.toString(resultSet.getDouble("itemPrice"));
				String itemDescription = resultSet.getString("itemDesc");
				
				returnItem = new Item(Integer.parseInt(itemID), itemCode, itemName,Double.parseDouble(itemPrice),itemDescription);     
			}

			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return returnItem;
	}
	
	
	
	//method to remove an item
	public String removeItem(String itemId) {
		String output ="";
		
		try {
			Connection con = connect();
			if(con==null) {
				output ="Error while Connecfting to the database ";
				
			}
			
			String query = "delete from items where itemId="+itemId+" ";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.executeUpdate();
			con.close();
			
		}catch(Exception e) {
			
			output =" error while deleting an item ";
			e.printStackTrace();
		}
		
		return output;
	}

	
	//method to update Items
	public String updateItem(int id, String code, String name, double price, String desc) {
		
		String output="";
		try {
		Connection con = connect();
		if(con==null) {
			output="Error While Connecting to the Database!!!";
		}
		
		String query = "update items set itemCode = ? , itemName = ? , itemPrice = ? , itemDesc = ? where itemID = ?";
		
		PreparedStatement stmt = con.prepareStatement(query);
		
		stmt.setString(1, code);
		stmt.setString(2, name);
		stmt.setDouble(3, price);
		stmt.setString(4, desc);
		stmt.setInt(5, id);
		
		stmt.execute();
		con.close();
		
		output ="Sucessfully Updated !!!";
		
		}catch(Exception e) {
			
			output ="Error while Updating an Item ";
			e.printStackTrace();
		}
		
		
		return output;
	}
}
