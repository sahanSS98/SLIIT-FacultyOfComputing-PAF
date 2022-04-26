package com.electrogridpaf.jaxrs.crud.billingSector.resources;

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

import com.electrogridpaf.jaxrs.crud.billingSector.services.billingSecServices;

@Path("/billsSector")
public class billingSecResource {
	billingSecServices billServ = new billingSecServices();
	
	//View Bill
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllBills(){
		return billServ.viewAllBills();
	}
	
	//Insert Bill
	@POST
	@Path("/insertBillDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBillData(@FormParam("b_no") String b_no, @FormParam("b_desc") String b_desc, @FormParam("b_type") String b_type, @FormParam("unit") String unit,
								@FormParam("c_id") String c_id) 
	{
		
		String output = billServ.insertBillDetails(b_no, b_desc, b_type, unit, c_id) ;
		return output;
	}
	
	//View Bill By ID
	@GET
	@Path("/{c_id}")
	@Produces(MediaType.TEXT_HTML)
	public String getBillDataById(@PathParam("c_id") int id){
		return billServ.viewBillDetails(id);
	}
	
	//Update Bill
	@PUT
	@Path("/updateBillDetails")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBillData(@FormParam("b_no") String b_no, @FormParam("b_type") String b_type,
			@FormParam("b_desc") String b_desc, @FormParam("unit") String unit, @FormParam("b_id") String b_id) {
			
		String output = billServ.updateBillDetails(b_id, b_no, b_type, b_desc, unit);
		
		return output;
	}
	
	//Delete Bill
	@DELETE
	@Path("/deleteBillDetails")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBillData(String bill)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(bill, "", Parser.xmlParser());
		
		//Read the value from the element customer id
		String b_id = doc.select("b_id").text();
		String output = billServ.deleteBillDetails(b_id);
	
	return output;
	}

}
