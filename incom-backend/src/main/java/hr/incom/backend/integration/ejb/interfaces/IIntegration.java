package hr.incom.backend.integration.ejb.interfaces;

public interface IIntegration
{
	public String processCashPayment(String message) throws Exception;
}
