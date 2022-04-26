package com.electrogridpaf.jaxrs.crud.customerSector.services;

import java.sql.*;

public class customerSecServices{

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

	//insert customer
	public String insertCustomerDetails(String c_name, String c_email, String c_mobile) {
		
		String output = "";

		try {
			
			Connection con = connect();
			
			if(con == null)
			{return "Error while connecting to the database for inserting data";}
			
			String insertQuery = "insert into customersector (`c_id`, `c_name`, `c_email`, `c_mobile`)" + "values(?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(insertQuery);
			ps.setInt(1, 0);
			ps.setString(2, c_name);
			ps.setString(3, c_email);
			ps.setString(4, c_mobile);

			ps.execute();
			con.close();
			
			output = "Inserted Successfully";

		} catch(Exception e) {
			output = "Error While inserting the customer details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	//view customer details
	public String viewCustomerDetails() {
		
		String output ="";
		
		try {
			
			Connection con = connect();
			
			if (con==null)
			{ return "Error!! While connecting to the database for read the customer details";}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Name</th><th>Mobile</th>" +
			"<th>Email</th>" +
			"<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from customersector";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				
				String c_id = Integer.toString(rs.getInt("c_id"));
				String c_name = rs.getString("c_name");
				String c_mobile = rs.getString("c_mobile");
				String c_email = rs.getString("c_email");
				
				// Add into the html table
				output += "<tr><td>" + c_name + "</td>";
				output += "<td>" + c_mobile + "</td>";
				output += "<td>" + c_email + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
				+ "<td><form method='post' action='items.jsp'>"
				+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
				+ "<input name='cus_id' type='hidden' value='" + c_id
				+ "'>" + "</form></td></tr>";
				
			}
			
			con.close();
			
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading customer details";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//update customer details
	public String updateCustomerDetails(String c_id, String c_name, String c_mobile, String c_email) {
		
		String output="";
		
		try {
			
			Connection con = connect();
			
			if (con==null)
			{ return "Error!! While connecting to the database for updating the " + c_name;}
			
			// create a prepared statement
			String query = "UPDATE customersector SET c_name=?, c_mobile=?, c_email=? WHERE c_id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, c_name);
			preparedStmt.setString(2, c_mobile);
			preparedStmt.setString(3, c_email);
			preparedStmt.setInt(4,Integer.parseInt(c_id));
			
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			
			output = "Updated successfully";
			
		} catch (Exception e) {
			
			output = "Error while updating the " + c_name;
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	//delete Customer from db
	public String deleteCustomerDetails(String c_id)
	{
		String output = "";
		try
		{
		Connection con = connect();
		
		if (con == null)
		{return "Error while connecting to the database for deleting."; }
		
		// create a prepared statement
		String query = "delete from customersector WHERE c_id=?";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(c_id));
		
		// execute the statement
		preparedStmt.execute();
		
		con.close();
		output = "Deleted successfully";
		}
		catch (Exception e)
		{
			output = "Error while deleting the customer details.";
			System.err.println(e.getMessage());
		}
	return output;
	}
	
	//get customer by id
	public String getCustomerDetailsById(int c_id) {
		String output ="";
		
		try {
			
			Connection con = connect();
			
			if (con==null)
			{ return "Error!! While connecting to the database for read the customers";}
			
			
			
			String selquery = "select * from customersector WHERE c_id= " + c_id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selquery);
			
			while(rs.next()) {
				
				
				String c_name = rs.getString("c_name");
				String c_mobile = rs.getString("c_mobile");
				String c_email = rs.getString("c_email");
				
				// Add into the html table
				output += "Name: " + c_name + "<br>" + "Mobile No: " + c_mobile;
				output += "<br>Email: " + c_email;
				
			}
			
			con.close();
			
		} catch (Exception e) {
			output = "Error while reading customer";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
