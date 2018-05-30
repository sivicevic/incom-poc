package hr.incom.backend.integration.ejb.interfaces;

public interface IIntegration
{
	public String processCashPayment(String message) throws Exception;
	
	public String processFile(String file, String key) throws Exception;
}
