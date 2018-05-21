package hr.incom.service.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import hr.incom.backend.integration.ejb.interfaces.IIntegration;

@Path("/fiskal")
public class IntegrationRestWS {
	
	@EJB
	private IIntegration integration;

	@POST
	@Path("/send")
	@Produces({"application/xml", "application/json"})
	@Consumes({"application/xml", "application/json"})
	public String processMessage(String name)
	{
		String response = "";

		try
		{
			response = integration.processCashPayment(name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return response;
	}

}
