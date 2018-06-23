package hr.incom.backend.integration.ejb;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import hr.incom.backend.information.IInformation;
import hr.incom.shared.helper.DigitalSignatureVerify;
import hr.incom.shared.integration.ejb.interfaces.IIntegration;

@Stateless
@LocalBean
public class Integration implements IIntegration
{
	
	public static final String FILE_PATH = "./tmp/";
	public static final String ORIGIN_FILE_PATH = "./tmp/fiskalFiles/unsigned/";
	public static final String DESTINATION_FILE_PATH = "./tmp/fiskalFiles/signed/";
	public static final String DESTINATION_FILE_PATH2 = "./tmp/fiskalFiles/signed2/";
	public static final String KEYS_PATH = "./tmp/keys/";

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

		return String.valueOf(result);
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
	
	@Override
	public String processInvoice(Document doc) throws Exception {
		boolean isValid = false;	
		String publicKeyPath = KEYS_PATH + "publickey.key";
		try
		{
			isValid = DigitalSignatureVerify.isValid(doc, publicKeyPath);
		}
		catch (Exception e)
		{
			throw new Exception("Unable to validate file");
		}
		System.out.println("Validation: " + isValid);
		return String.valueOf(isValid);
	}

}
