package hr.incom.backend.integration.ejb;

import java.io.ByteArrayInputStream;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import hr.incom.backend.helper.DigitalSignatureUtils;
import hr.incom.backend.information.IInformation;
import hr.incom.backend.integration.ejb.interfaces.IIntegration;

@Stateless
@Local
public class Integration implements IIntegration
{
	@EJB
	private IInformation information;

	@Override
	public String processCashPayment(String message) throws Exception
	{
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		message = message.replaceAll("\n", "");
		try
		{
			doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(message.getBytes()));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		boolean result = DigitalSignatureUtils.isValid(doc);

		//information.storeInvoice();

		//System.out.println("Testiram");

		return String.valueOf(result); // + result;
	}

}
