package hr.incom.shared.integration.ejb.interfaces;


import org.w3c.dom.Document;

public interface IIntegration
{
	public String processCashPayment(String message) throws Exception;
	
	public String processFile(String file, String key) throws Exception;
	
	public String processInvoice(Document doc) throws Exception;
}
