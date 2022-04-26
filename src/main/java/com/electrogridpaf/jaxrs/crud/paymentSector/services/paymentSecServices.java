package com.electrogridpaf.jaxrs.crud.paymentSector.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class paymentSecServices {
	
	//connection
		private Connection connect() {
			Connection con = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				String url = String.format("jdbc:mysql://localhost:3306/electrogridpaf");
				String username = "root";
				String password = "";
				
				con = DriverManager.getConnection(url,username, password);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
		
		//insert payment details
		public String insertPaymentsDetails(String p_type, String amount, String c_id, String b_id) {
			
			String output = "";
			
			try {
				
				Connection con = connect();
				
				if(con == null)
				{return "Error while connecting to the database for inserting data";}
				
				String insertQuery = "insert into paymentSector (`p_id`, `p_type`,`amount`, `c_id`, `b_id`)" + "values(?,?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertQuery);
				ps.setInt(1, 0);
				ps.setString(2, p_type);
				ps.setString(3, amount);
				ps.setString(4, c_id);
				ps.setString(5, b_id);

				ps.execute();
				con.close();

			} catch(Exception e) {
				output = "Payment does not go through.. somthing went wrong!.";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//view all payments
		public String viewPaymentsDetails() {
			
			String output ="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for read the payment details";}
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>id</th><th>Payment Type</th>" +
				"<th>Amount (Rs.)</th>" +
				"<th>Update</th><th>Remove</th></tr>";
				
				String query = "select * from paymentSector";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					
					String p_id = Integer.toString(rs.getInt("p_id"));
					String p_type = rs.getString("p_type");
					String amount = rs.getString("amount");
					
					// Add into the html table
					output += "<tr><td>" + p_id + "</td>";
					output += "<td>" + p_type + "</td>";
					output += "<td>" + amount + "</td>";
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
					+ "<td><form method='post' action='items.jsp'>"
					+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
					+ "<input name='cus_id' type='hidden' value='" + p_id
					+ "'>" + "</form></td></tr>";
					
				}
				
				con.close();
				
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading payments";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		//update payment --> Payment update is unnecessary <--
		public String updatePaymentsDetails(String p_id, String p_type, String amount) {
			
			String output="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for updating the " + p_id;}
				
				// create a prepared statement
				String query = "UPDATE paymentSector SET p_type=?, amount=?, WHERE c_id=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, p_type);
				preparedStmt.setString(2, amount);
				preparedStmt.setInt(3,Integer.parseInt(p_id));
				
				// execute the statement
				preparedStmt.execute();
				
				con.close();
				
				output = "Updated payment details successfully";
				
			} catch (Exception e) {
				
				output = "Error while updating the " + p_id;
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//delete
		public String deletePaymentsDetails(String p_id)
		{
			String output = "";
			try
			{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			
			// create a prepared statement
			String query = "delete from paymentSector WHERE p_id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(p_id));
			
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			output = "<h1>Deleted paymen detail successfully</h1>";
			}
			catch (Exception e)
			{
				output = "Error while deleting the payment detail.";
				System.err.println(e.getMessage());
			}
		return output;
		}
		
}
