package com.electrogridpaf.jaxrs.crud.paymentSector.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.electrogridpaf.jaxrs.crud.paymentSector.services.paymentSecServices;

@Path("/paymentsSector")
public class paymentSecResource {

	paymentSecServices payServ = new paymentSecServices();
	
	//View all payments
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String viewPaymentsData(){
		return payServ.viewPaymentsDetails();
	}
	
	//insert payments
	@POST
	@Path("/addPaymentDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String insertPaymentsData(@FormParam("p_type") String p_type, @FormParam("amount") String amount,
								@FormParam("c_id") String c_id, @FormParam("b_id") String b_id) 
	{
		
		String output = payServ.insertPaymentsDetails(p_type, amount, c_id, b_id);
		return output + "<h1>Your Payment is success..!</h1>";
	}
	
	@PUT
	@Path("/updatePaymentDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePaymentsData(@FormParam("p_type") String p_type, @FormParam("amount") String amount,
			@FormParam("p_id") String p_id) {
			
		String output = payServ.updatePaymentsDetails(p_id, p_type, amount);
		
		return output;
	}
	
	
	//Delete payment
	@DELETE
	@Path("/removePaymentDetails")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePaymentsData(String payments)
	{
		//Convert the input string to an XML document
		Document pdoc = Jsoup.parse(payments, "", Parser.xmlParser());
		
		//Read the value from the element customer id
		String p_id = pdoc.select("p_id").text();
		String output = payServ.deletePaymentsDetails(p_id);
	
	return output;
	}
}
