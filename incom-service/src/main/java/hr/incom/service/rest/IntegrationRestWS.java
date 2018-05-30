package hr.incom.service.rest;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import hr.incom.backend.helper.DigitalSignatureGenerate;
import hr.incom.backend.helper.DigitalSignatureVerify;
import hr.incom.backend.information.Information;
import hr.incom.backend.integration.ejb.interfaces.IIntegration;

@Path("/fiskal")
public class IntegrationRestWS implements IIntegrationRestWS {
	
	public static final String ORIGIN_FILE_PATH = "D:\\fiskalFiles\\unsigned\\";
	public static final String DESTINATION_FILE_PATH = "D:\\fiskalFiles\\signed\\";
	public static final String KEYS_PATH = "C:\\fiskal\\incom-common\\src\\main\\resources\\keys\\";
	
	DigitalSignatureGenerate generateSignature = new DigitalSignatureGenerate();
	
	@EJB
	private IIntegration integration = new IIntegration() {
		
		@Override
		public String processFile(String fullPath, String keyPath) throws Exception {
			boolean isValid = false;
			
		
			try
			{
				isValid = DigitalSignatureVerify.isValid(fullPath, keyPath);
			}
			catch (Exception e)
			{
				throw new Exception("Unable to validate file");
			}

			return String.valueOf(isValid);
		}
		
		
		
		@Override
		public String processCashPayment(String message) throws Exception {
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


			return String.valueOf(result); // + result;
		}
	};

	@GET
	@Path("/send/{type}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public Response processMessage(@PathParam("type") String type)
	{
		Information information = new Information();
		String response = "";
		StringBuilder stringBuilder = new StringBuilder();
		Date date = new Date();
		LocalDateTime pocetak;
		
		stringBuilder.append("Invoice.");
		//stringBuilder.append(date.getTime());
		//stringBuilder.append(".");
		stringBuilder.append(type);

		String fileName = stringBuilder.toString();
		String fullPathOrigin = ORIGIN_FILE_PATH + fileName;
		String fullPathDestination = DESTINATION_FILE_PATH + fileName;
		String privateKeyPath = KEYS_PATH + "privatekey.key";
		String publicKeyPath = KEYS_PATH + "publickey.key";
		generateSignature.generateXMLDigitalSignature(fullPathOrigin, fullPathDestination, privateKeyPath, publicKeyPath);
		

    	try
		{
			//response = integration.processCashPayment(type);
			pocetak = LocalDateTime.now();
    		
			response = integration.processFile(fullPathDestination, publicKeyPath);
			LocalDateTime kraj = LocalDateTime.now();
			
			Duration duration = Duration.between(kraj, pocetak);
    		long diff = Math.abs(duration.toMillis());
    		System.out.println(diff);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			return Response.status(500).entity("Error!").build();
		}

    	if (StringUtils.equals(response, "true")) {
    		
    		information.insertIntoInvoice(fullPathDestination);
    		return Response.status(200).entity("Success!").build();

    	}
    	else {
    		return Response.status(500).entity("Validation failed!").build();
    	}

		
	}

}
