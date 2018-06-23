package hr.incom.shared.helper;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import hr.incom.shared.integration.ejb.interfaces.IIntegration;


public class RestUtil {
	
	public static final String ORIGIN_FILE_PATH = "./tmp/fiskalFiles/unsigned/";
	public static final String DESTINATION_FILE_PATH = "./tmp/fiskalFiles/signed/";
	//public static final String DESTINATION_FILE_PATH_LOCAL = "D:\\weblogic.Server\\tmp\\fiskalFiles\\signed"; // FIXME Change path when clinet is in cloud!
	public static final String DESTINATION_FILE_PATH_LOCAL = "./tmp/fiskalFiles/signed/"; // FIXME Change path when clinet is in cloud!
	public static final String SERVER_FILE_PATH = "/home/oracle/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/tmp/fiskalFiles/signed/";
	public static final String DESTINATION_FILE_PATH2 = "./tmp/fiskalFiles/signed2/";
	public static final String KEYS_PATH = "./tmp/keys/";
	public static final String RESPONSE_FILE_PATH = "./tmp/fiskalFiles/response/";
	public static final String DESTINATION_FILE_PATH_RESPONSE = "./tmp/fiskalFiles/response/signed/";
	
	//private static final Logger LOG = LoggerFactory.getLogger(RestUtil.class);
	
	DigitalSignatureGenerate generateSignature = new DigitalSignatureGenerate();
	
	private IIntegration integration = new IIntegration() {
		
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
			return String.valueOf(isValid);
		}
	};

	
	public void createInvoices(String type, int i)
	{

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Invoice-" + i + ".");
		stringBuilder.append(type);
		String fileName = stringBuilder.toString();
		String fullPathOrigin = ORIGIN_FILE_PATH + "Invoice.xml";
		String fullPathDestination = DESTINATION_FILE_PATH + fileName;
		String privateKeyPath = KEYS_PATH + "privatekey.key";
		String publicKeyPath = KEYS_PATH + "publickey.key";
		generateSignature.generateXMLDigitalSignature(fullPathOrigin, fullPathDestination, privateKeyPath, publicKeyPath);

	}
	
	public String createResponse(long id)
	{

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Response-" + id + ".xml");
		String fileName = stringBuilder.toString();
		String fullPathOrigin = RESPONSE_FILE_PATH + "Response.xml";
		String fullPathDestination = DESTINATION_FILE_PATH_RESPONSE + fileName;
		String privateKeyPath = KEYS_PATH + "privatekey.key";
		String publicKeyPath = KEYS_PATH + "publickey.key";
		System.out.println(fileName);
		generateSignature.generateXMLDigitalSignature(fullPathOrigin, fullPathDestination, privateKeyPath, publicKeyPath);
		
		return fullPathDestination;

	}
	
	public String validateResponseXml(String responsePath) {
		String jir = null;
		boolean valid;
		String publicKeyPath = KEYS_PATH + "publickey.key";
		
		Document doc = generateSignature.getXmlDocument(responsePath);
		
		try {
			valid = DigitalSignatureVerify.isValid(doc, publicKeyPath);
			
			if(valid) {
				jir = doc.getElementsByTagName("tns:Jir").item(0).getFirstChild().getNodeValue();
			}
			else {
				jir = "INVALID";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jir;
	}
	

	public long processMessage(String type, int i)
	{
		String response = "";
		StringBuilder stringBuilder = new StringBuilder();
		LocalDateTime pocetak;
		stringBuilder.append("Invoice-" + i + ".");
		stringBuilder.append(type);
		String fileName = stringBuilder.toString();
		String fullPathOrigin = ORIGIN_FILE_PATH + "Invoice.xml";
		String fullPathDestination = DESTINATION_FILE_PATH2 + fileName;
		String privateKeyPath = KEYS_PATH + "privatekey.key";
		String publicKeyPath = KEYS_PATH + "publickey.key";
		generateSignature.generateXMLDigitalSignature(fullPathOrigin, fullPathDestination, privateKeyPath, publicKeyPath);
		
		pocetak = LocalDateTime.now();
		
    	validateSignature(response, pocetak, fullPathDestination, publicKeyPath);
    	
    	return measureTimeInMilis(pocetak);
		
	}
	
	
	private Response validateSignature(String response, LocalDateTime pocetak,
			String fullPathDestination, String publicKeyPath) {
		try
		{
			response = integration.processFile(fullPathDestination, publicKeyPath);
		}
		catch (Exception e)
		{
			e.printStackTrace();		
			return Response.status(500).entity("Error!").build();
		}

    	if (StringUtils.equals(response, "true")) {
    		//information.prepareInsertFromString(fullPathDestination);
    		return Response.status(200).entity("Success!").build();
    	}
    	else {
    		return Response.status(500).entity("Validation failed!").build();
    	}
	}

	public long measureTimeInMilis(LocalDateTime pocetak) {
		LocalDateTime kraj = LocalDateTime.now();
		Duration duration = Duration.between(kraj, pocetak);
		return Math.abs(duration.toMillis());
	}
	
	public ArrayList<String> getXMLFilesAsString(String serverIP) {
	    List<String> aList = new ArrayList<String>();
	    File folder;
	    URI link = null;
	    
	    if (serverIP != null) {
	    	String fullPath = "http:/\\/\\"+serverIP+SERVER_FILE_PATH;
	    	folder = new File(fullPath);
	    }
	    else { 
	    	folder = new File(DESTINATION_FILE_PATH); 
	    }
	    
	    
	    
	    File[] files = folder.listFiles();
	    for (File pf : files) {

	      if (pf.isFile() && getFileExtensionName(pf).indexOf("xml") != -1) {
	    	  
	    	  aList.add(convertDocumentToString(generateSignature.getXmlDocument(pf.toString())));
	        
	      }
	    }
	    return (ArrayList<String>) aList;
	  }
	
	
	public ArrayList<Document> getXMLFilesAsDoc() {
	    List<Document> aList = new ArrayList<Document>();
	    
	    File folder = new File(DESTINATION_FILE_PATH_LOCAL);
	    
	    File[] files = folder.listFiles();
	    for (File pf : files) {

	      if (pf.isFile() && getFileExtensionName(pf).indexOf("xml") != -1) {
	    	  
	    	  aList.add(generateSignature.getXmlDocument(pf.toString()));
	        
	      }
	    }
	    return (ArrayList<Document>) aList;
	  }
	
	public Document getXMLFileAsDoc(int i) {
		
		String workingDir = System.getProperty("user.dir");
		
	    File f = new File(workingDir + "/signed" + "/Invoice-" + i + ".xml");
	    
	    System.out.println("File: " + f.toString());
	    
	      if (f.isFile() && getFileExtensionName(f).indexOf("xml") != -1) {
	    	  return generateSignature.getXmlDocument(f.toString());
	      }
	      else {
	    	  f = new File(workingDir + "/target/signed" + "/Invoice-" + i + ".xml");
	    	  if (f.isFile() && getFileExtensionName(f).indexOf("xml") != -1) {
		    	  return generateSignature.getXmlDocument(f.toString());
		      }
	      }
	      
	      return null;
	  }

	  public static String getFileExtensionName(File f) {
	    if (f.getName().indexOf(".") == -1) {
	      return "";
	    } else {
	      return f.getName().substring(f.getName().length() - 3, f.getName().length());
	    }
	  }
	  
	  
	  private static String convertDocumentToString(Document doc) {
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer;
	        try {
	            transformer = tf.newTransformer();
	            // below code to remove XML declaration
	            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	            StringWriter writer = new StringWriter();
	            transformer.transform(new DOMSource(doc), new StreamResult(writer));
	            String output = writer.getBuffer().toString();
	            return output;
	        } catch (TransformerException e) {
	            e.printStackTrace();
	        }
	        
	        return null;
	    }
}
