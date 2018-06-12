package hr.incom.backend.business;

import javax.ejb.Stateless;

import org.w3c.dom.Document;

import hr.incom.backend.helper.DigitalSignatureGenerate;
import hr.incom.backend.helper.DigitalSignatureVerify;

@Stateless
public class Business
{
	public boolean validateCashInvoice(String message) throws Exception
	{
		boolean isValid = false;

		Document doc = DigitalSignatureGenerate.readDocument("InputMessage.xml");
		try
		{
			isValid = DigitalSignatureVerify.isValid(doc);
		}
		catch (Exception e)
		{
			throw new Exception("Unable to validate message");
		}

		return isValid;
	}
}
