package com.electrogridpaf.jaxrs.crud.employeeSector.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.electrogridpaf.jaxrs.crud.employeeSector.services.employeeSecServices;

@Path("/employeeSector")
public class employeeSecResources {

	employeeSecServices empServ = new employeeSecServices();
	
	//view all employees
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String viewAllEmpData() {
		return empServ.viewEmpDetails();
	}
	
	
	//Add new employee
	@POST
	@Path("/addEmployeeDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertEmpData(@FormParam("emp_name") String emp_name, @FormParam("emp_mobile") String emp_mobile,
								@FormParam("emp_designation") String emp_designation) 
	{
		
		String output = empServ.insertEmpDetails(emp_name, emp_mobile, emp_designation);
		 return output;
	}
	
	//View Employee by ID
	@GET
	@Path("/{e_id}")
	@Produces(MediaType.TEXT_HTML)
	public String getEmpDataById(@PathParam("emp_id") int id){
		return empServ.getEmpDetailsById(id);
	}
	
	//Update Employee
	@PUT
	@Path("/updateEmployeeDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateEmpData(@FormParam("emp_name") String emp_name, @FormParam("emp_mobile") String emp_mobile,
			@FormParam("emp_designation") String emp_designation, @FormParam("emp_id") String emp_id) {
			
		String output = empServ.updateEmpDetails(emp_id, emp_name, emp_mobile, emp_designation);
		
		return output;
	}
	
	//Remove employee
	@DELETE
	@Path("/deleteEmployeeDetails")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteEmpData(String emp)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(emp, "", Parser.xmlParser());
		
		//Read the value from the element <e_id>
		String emp_id = doc.select("emp_id").text();
		String output = empServ.deleteEmpDetails(emp_id);
	
	return output;
	}
}
