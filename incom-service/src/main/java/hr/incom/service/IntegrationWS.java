package hr.incom.service;

import javax.jws.WebService;

import hr.incom.shared.integration.ejb.interfaces.IIntegration;


@WebService(endpointInterface = "hr.incom.service.IIntegrationWS")
public class IntegrationWS implements IIntegrationWS
{

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
