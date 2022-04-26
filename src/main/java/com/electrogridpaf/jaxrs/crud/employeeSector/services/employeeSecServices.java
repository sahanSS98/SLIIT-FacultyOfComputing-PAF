package com.electrogridpaf.jaxrs.crud.employeeSector.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class employeeSecServices {

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

		//insert employee
		public String insertEmpDetails(String emp_name, String emp_mobile, String emp_designation) {
			
			String output = "";

			try {
				
				Connection con = connect();
				
				if(con == null)
				{return "Error while connecting to the database for inserting data";}
				
				String insertEmp = "insert into employeesector (`emp_id`, `emp_name`, `emp_mobile`, `emp_designation`)" + "values(?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertEmp);
				ps.setInt(1, 0);
				ps.setString(2, emp_name);
				ps.setString(3, emp_mobile);
				ps.setString(4, emp_designation);

				ps.execute();
				con.close();
				
				output = "Inserted "+ emp_name + " Successfully";

			} catch(Exception e) {
				output = "Error While inserting the employee.";
				System.err.println(e.getMessage());
			}

			return output;
		}

		//view employee details
		public String viewEmpDetails() {
			
			String output ="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for read the Employee Details";}
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Employee Name</th><th>Mobile No:</th>" +
				"<th>Email</th>" +
				"<th>Update</th><th>Remove</th></tr>";
				
				String query = "select * from employeeSector";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					
					String emp_id = Integer.toString(rs.getInt("emp_id"));
					String emp_name = rs.getString("emp_name");
					String emp_mobile = rs.getString("emp_mobile");
					String emp_designation = rs.getString("emp_designation");
					
					// Add into the html table
					output += "<tr><td>" + emp_name + "</td>";
					output += "<td>" + emp_mobile + "</td>";
					output += "<td>" + emp_designation + "</td>";
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
					+ "<td><form method='post' action='items.jsp'>"
					+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
					+ "<input name='cus_id' type='hidden' value='" + emp_id
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
		
		//update employee details
		public String updateEmpDetails(String emp_id, String emp_name, String emp_mobile, String emp_designation) {
			
			String output="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for updating the " + emp_name;}
				
				// create a prepared statement
				String query = "UPDATE employeesector SET emp_name=?, emp_mobile=?, emp_designation=? WHERE emp_id=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, emp_name);
				preparedStmt.setString(2, emp_mobile);
				preparedStmt.setString(3, emp_designation);
				preparedStmt.setInt(4,Integer.parseInt(emp_id));
				
				// execute the statement
				preparedStmt.execute();
				
				con.close();
				
				output = "Updated successfully";
				
			} catch (Exception e) {
				
				output = "Error while updating the " + emp_name;
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		//delete employee from db
		public String deleteEmpDetails(String emp_id)
		{
			String output = "";
			
			try
			{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			
			// create a prepared statement
			String query = "delete from employeesector where emp_id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(emp_id));
			
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			output = "Deleted successfully";
			}
			catch (Exception e)
			{
				output = "Error while deleting the employee details.";
				System.err.println(e.getMessage());
			}
		return output;
		}
		
		//get employee by id
		public String getEmpDetailsById(int emp_id) {
			String output ="";
			
			try {
				
				Connection con = connect();
				
				if (con==null)
				{ return "Error!! While connecting to the database for read the employee details";}
				
				
				
				String selquery = "select * from employeeSector WHERE emp_id= " + emp_id;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(selquery);
				
				while(rs.next()) {
					
					
					String emp_name = rs.getString("emp_name");
					String emp_mobile = rs.getString("emp_mobile");
					String emp_desig = rs.getString("emp_designation");
					
					// view
					output += "Name: " + emp_name + "<br>" + "Mobile No: " + emp_mobile;
					output += "<br>Job Role: " + emp_desig;
					
					
				}
				
				con.close();
				
			} catch (Exception e) {
				output = "Error while reading employee";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
}
