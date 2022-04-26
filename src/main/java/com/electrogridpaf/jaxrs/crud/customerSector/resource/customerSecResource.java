package com.electrogridpaf.jaxrs.crud.customerSector.resource;

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

import com.electrogridpaf.jaxrs.crud.customerSector.services.customerSecServices;


@Path("/customerSector")
public class customerSecResource {

customerSecServices services = new customerSecServices();
	
	//View all customers
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String viewCustomerData(){
		return services.viewCustomerDetails();
	}

	//Insert new Customer
	@POST
	@Path("/addCustomerDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomerData(@FormParam("c_name") String cus_name, @FormParam("c_mobile") String cus_mobile,
								@FormParam("c_email") String cus_email) 
	{
		
		String output = services.insertCustomerDetails(cus_name, cus_email, cus_mobile) ;
		 return output;
	}
	
	//update customer
	/*
	@PUT
	@Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(String customer) {
		

		JsonObject jelem = new JsonParser().parse(customer).getAsJsonObject();
		
		String cus_id = jelem.get("cus_id").getAsString();
		String cus_name = jelem.get("cus_name").getAsString();
		String cus_mobile = jelem.get("cus_mobile").getAsString();
		String cus_email = jelem.get("cus_email").getAsString();
		
		
		String output = services.updateCustomer(cus_id, cus_name, cus_mobile, cus_email);
		
		return output;
	}*/
	
	@PUT
	@Path("/updateCustomerDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomerData(@FormParam("c_name") String cus_name, @FormParam("c_mobile") String cus_mobile,
			@FormParam("c_email") String cus_email, @FormParam("c_id") String cus_id) {
			
		String output = services.updateCustomerDetails(cus_id, cus_name, cus_mobile, cus_email);
		
		return output;
	}
	
	
	//Delete Customer
	@DELETE
	@Path("/deleteCustomerDetails")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomerData(String cus)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(cus, "", Parser.xmlParser());
		
		//Read the value from the element customer id
		String cus_id = doc.select("c_id").text();
		String output = services.deleteCustomerDetails(cus_id);
	
	return output;
	}
	
	//Get customer by ID
	@GET
	@Path("/{c_id}")
	@Produces(MediaType.TEXT_HTML)
	public String getCustomerById(@PathParam("c_id") int id){
		return services.getCustomerDetailsById(id);
	}
}
