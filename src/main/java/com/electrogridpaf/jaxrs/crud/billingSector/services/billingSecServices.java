package com.electrogridpaf.jaxrs.crud.billingSector.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class billingSecServices {
		
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
		
		//add billing
		public String insertBillDetails(String b_no, String b_desc, String b_type,String unit, String c_id) {
			
			String output = "";

			try {
				
				Connection con = connect();
				
				if(con == null)
				{return "Error while connecting to the database for inserting data";}
				
				String insertQuery = "insert into billssector (`b_no`, `b_desc`, `b_type`, `unit`, `c_id`)" + "values(?,?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertQuery);
				
				ps.setString(1, b_no);
				ps.setString(2, b_desc);
				ps.setString(3, b_type);
				ps.setString(4, unit);
				ps.setString(5, c_id);

				ps.execute();
				con.close();
				
				output = "Inserted Successfully";

			} catch(Exception e) {
				output = "Error While inserting the bill details.";
				System.err.println(e.getMessage());
			}

			return output;
		}
		
		//update billing
		public String updateBillDetails(String b_id, String b_no, String b_type, String b_desc,String unit ) {
			
			String output="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for updating the " + b_id;}
				
				// create a prepared statement
				String query = "UPDATE billssector SET b_no=?, b_type=?, b_desc=?, unit=? WHERE c_id=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, b_no);
				preparedStmt.setString(2, b_type);
				preparedStmt.setString(3, b_desc);
				preparedStmt.setString(4, unit);
				preparedStmt.setInt(5,Integer.parseInt(b_id));
				
				// execute the statement
				preparedStmt.execute();
				
				con.close();
				
				output = "Updated successfully";
				
			} catch (Exception e) {
				
				output = "Error while updating the " + b_no;
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//view all billing
		public String viewAllBills() {
			
			String output ="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for read the Employee Details";}
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>BILL ID</th><th>CUSTOMER NAME</th>" 
				+ "<th>BILL NO</th>" + "<th>BILL DESCRIPTION</th>"+ "<th>BILL TYPE</th>" + "<th>UNIT</th>" + "<th>DATE</th>" +
				"<th>Update</th><th>Remove</th></tr>";
				
				String query = "select * from billssector b, customersector c where c.c_id=b.c_id";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					
					String bid = Integer.toString(rs.getInt("b_id"));
					String cusname = rs.getString("c_name");
					String billno = rs.getString("b_no");
					String billdesc = rs.getString("b_desc");
					String billtype = rs.getString("b_type");
					String units = rs.getString("unit");
					String curDate = rs.getString("curr_date");
					
					// Add into the html table
					output += "<tr><td>" + bid + "</td>";
					output += "<td>" + cusname + "</td>";
					output += "<td>" + billno + "</td>";
					output += "<td>" + billdesc + "</td>";
					output += "<td>" + billtype + "</td>";
					output += "<td>" + units + "</td>";
					output += "<td>" + curDate + "</td>";
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
					+ "<td><form method='post' action=''>"
					+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
					+ "<input name='cus_id' type='hidden' value='" + bid
					+ "'>" + "</form></td></tr>";
					
				}
				
				con.close();
				
				output += "</table>";
			} catch (Exception e) {
				output = "Error while fetching employees details.";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		//view Billing by customer id
		public String viewBillDetails(int cus_id) {
			
			String output ="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for read the bill";}
				
				String query = "select * from billssector b, customerSector c where b.c_id= " + cus_id + "&& c.c_id = b.c_id";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					
					String c_id = Integer.toString(rs.getInt("c_id"));
					String c_name = rs.getString("c_name");
					String b_no = rs.getString("b_no");
					String curdate = rs.getString("curr_date");
					String units = rs.getString("unit");
					
					int unit = rs.getInt("unit");
					double curr_amount = 0;
					
					if(unit<=60) {
						curr_amount = (double) (unit * 7.85);
					}else if(unit>60 && unit<=90){
						curr_amount = (double) ((double) (60 * 7.85) + (unit - 60) * 10.00);
					}else if(unit>90 && unit<=120){
						curr_amount = (double) ((double) (60 * 7.85) + (30 * 10.00) + (unit - 90) * 27.75);
					}else if(unit>120 && unit<=180){
						curr_amount = (double) ((double) (60 * 7.85) + (30 * 10.00) + (30 *27.75) + (unit - 120) * 32.75);
					}else {
						curr_amount = (double) ((double) (60 * 7.85) + (30 * 10.00) + (30 * 27.75) + (60 * 32.75) + (unit - 180) * 45.00);
					}
					
					// Add into the html table
					output = "Customer ID - " + c_id + "<br>Name: " +c_name + "<br>Bill No- "+b_no+ "<br>Units- "+ units + "<br>Date- " + curdate + "<br>Amount - Rs. " +  curr_amount;
					
				}
				
				con.close();
				
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading bill";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		
		//Delete billing
		public String deleteBillDetails(String bId)
		{
			String output = "";
			try
			{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			
			// create a prepared statement
			String query = "delete from billssector WHERE b_id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(bId));
			
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			output = "Deleted successfully";
			}
			catch (Exception e)
			{
				output = "Error while deleting the bill.";
				System.err.println(e.getMessage());
			}
		return output;
		}
}
