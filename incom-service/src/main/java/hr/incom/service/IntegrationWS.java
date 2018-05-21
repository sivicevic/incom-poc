package hr.incom.service;

import javax.ejb.EJB;
import javax.jws.WebService;

import hr.incom.backend.integration.ejb.interfaces.IIntegration;

@WebService(endpointInterface = "hr.incom.service.IIntegrationWS")
public class IntegrationWS implements IIntegrationWS
{
	@EJB
	private IIntegration integration;

	@Override
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
