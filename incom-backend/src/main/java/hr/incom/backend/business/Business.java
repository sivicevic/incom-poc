package hr.incom.backend.business;

import javax.ejb.Stateless;

import org.w3c.dom.Document;

import hr.incom.backend.helper.DigitalSignatureUtils;

@Stateless
public class Business
{
	public boolean validateCashInvoice(String message) throws Exception
	{
		boolean isValid = false;

		Document doc = DigitalSignatureUtils.readDocument("InputMessage.xml");
		try
		{
			isValid = DigitalSignatureUtils.isValid(doc);
		}
		catch (Exception e)
		{
			throw new Exception("Unable to validate message");
		}

		return isValid;
	}
}
