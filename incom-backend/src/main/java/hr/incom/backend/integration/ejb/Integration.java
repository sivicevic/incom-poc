package hr.incom.backend.integration.ejb;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import hr.incom.backend.helper.DigitalSignatureVerify;
import hr.incom.backend.information.IInformation;
import hr.incom.backend.integration.ejb.interfaces.IIntegration;

@Stateless
@Local
public class Integration implements IIntegration
{
	@EJB
	private IInformation information;
	
	public static final String FILE_PATH = "D:\\fiskalFiles\\";

	@Override
	public String processCashPayment(String message) throws Exception
	{
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try
		{
			doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(message.getBytes()));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		boolean result = DigitalSignatureVerify.isValid(doc);

		//information.storeInvoice();

		//System.out.println("Testiram");

		return String.valueOf(result); // + result;
	}

	@Override
	public String processFile(String file, String key) throws Exception {
		
		boolean isValid = false;
						
		try
		{
			isValid = DigitalSignatureVerify.isValid(file, key);
		}
		catch (Exception e)
		{
			throw new Exception("Unable to validate file");
		}

		return String.valueOf(isValid);
	}

}
