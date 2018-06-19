package hr.incom.service.rest;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hr.incom.backend.integration.ejb.Integration;
import hr.incom.backend.integration.ejb.interfaces.IIntegration;
import hr.incom.common.procedures.InsertInvoice;


@Path("/fiskal")
public class IntegrationRestWS implements IIntegrationRestWS {
	

	RestUtil utils = new RestUtil();
	
	Integration integration = new Integration();
	
	
	@GET
	@Path("/send/{number}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public long restBulkRequestTest(@PathParam("number") int number) 
	{
		long transactionTime = 0;
		for(int i=1; i <= number; i++) {
			transactionTime = transactionTime + utils.processMessage("xml", i);
		}
		System.out.println(transactionTime);
		return transactionTime;
	}
	
	@GET
	@Path("/create/{number}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public long restCreateXmlTest(@PathParam("number") int number) 
	{
		long transactionTime = 0;
		for(int i=1; i <= number; i++) {
			utils.createInvoices("xml", i);
		}
		System.out.println(transactionTime);
		return transactionTime;
	}
	
	@POST
    @Path("/postxml/{IP}/{invNum}")
    public String restPostPureXML(@PathParam("IP") String serverIP, @PathParam("invNum") int invNum) {
		List<String> xmlList = utils.getXMLFilesAsString(serverIP);
		Document doc = null;
		String result = null;
		int size;
		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		LocalDateTime pocetak = LocalDateTime.now();
		
		if (invNum != 0) { 
			size = invNum;
		}
		else { 
			size = xmlList.size();
		}
		
		for (int i=size; i>0; i--) { 
			try {
				doc = dbf.newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(xmlList.get(i-1).getBytes("utf-8"))));
				
				try {
					result = integration.processInvoice(doc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Invoice no-" + i + " is processed as valid - " + result);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (StringUtils.equals(result, "true")) {
				
				insert.InsertIntoInvoice(doc, conn);
	    		//return Response.status(200).entity("Success!").build();
	    	}
		}
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		long duration = utils.measureTimeInMilis(pocetak);
		System.out.println("Transaction duration for postxml: " + duration);
		
        return "End of service call - ordinary for loop of xml strings! Duration: " + duration;
    }
	
	@POST
    @Path("/postxml-v1")
    public String restPostPureXMLV1(String serverIP) {
		List<String> xmlList = utils.getXMLFilesAsString(serverIP);
		Document doc = null;
		String result = null;
		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		LocalDateTime pocetak = LocalDateTime.now();
		for (int i=xmlList.size(); i>0; i--) { 
			try {
				doc = dbf.newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(xmlList.get(i-1).getBytes("utf-8"))));
				
				try {
					result = integration.processInvoice(doc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Invoice no-" + i + " is processed as valid - " + result);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (StringUtils.equals(result, "true")) {
				
				insert.InsertIntoInvoiceV1(doc, conn);
	    		//return Response.status(200).entity("Success!").build();
	    	}
		}
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		long duration = utils.measureTimeInMilis(pocetak);
		System.out.println("Transaction duration for postxml: " + duration);
		
        return "End of service call - ordinary for loop of xml strings! Duration: " + duration;
    }
	
	@POST
    @Path("/postxml2")
    public String restPostPureXMLStream(String serverIP) {
		List<String> items = utils.getXMLFilesAsString(serverIP);

		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		LocalDateTime pocetak = LocalDateTime.now();
		
		items.forEach(item->{
			try {
				Document doc = dbf.newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(item.getBytes("utf-8"))));
				String result = integration.processInvoice(doc);
				
				if (StringUtils.equals(result, "true")) {
					insert.InsertIntoInvoice(doc, conn);
		    		//return Response.status(200).entity("Success!").build();
		    	}
				//System.out.println("Invoice no-" + item + " is processed as valid - " + result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		long duration = utils.measureTimeInMilis(pocetak);
		System.out.println("Transaction duration for postxml2: " + duration);
		
		return "End of service call - sequential stream of xml strings! Duration: " + duration;
    }
	
	
	@POST
    @Path("/postxml2-v1")
    public String restPostPureXMLStreamV1(String serverIP) {
		List<String> items = utils.getXMLFilesAsString(serverIP);

		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		LocalDateTime pocetak = LocalDateTime.now();
		
		items.forEach(item->{
			try {
				Document doc = dbf.newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(item.getBytes("utf-8"))));
				String result = integration.processInvoice(doc);
				
				if (StringUtils.equals(result, "true")) {
					insert.InsertIntoInvoiceV1(doc, conn);
		    		//return Response.status(200).entity("Success!").build();
		    	}
				//System.out.println("Invoice no-" + item + " is processed as valid - " + result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		long duration = utils.measureTimeInMilis(pocetak);
		System.out.println("Transaction duration for postxml2: " + duration);
		
		return "End of service call - sequential stream of xml strings! Duration: " + duration;
    }
	
	@POST
    @Path("/postxml3")
    public String restPostPureXMLParallelStream(String serverIP) {
		List<String> items = utils.getXMLFilesAsString(serverIP);

		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		LocalDateTime pocetak = LocalDateTime.now();
		
		items.parallelStream().forEach(item->{
			try {
				Document doc = dbf.newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(item.getBytes("utf-8"))));
				String result = integration.processInvoice(doc);
				
				if (StringUtils.equals(result, "true")) {
					insert.InsertIntoInvoice(doc, conn);
		    		//return Response.status(200).entity("Success!").build();
		    	}
				//System.out.println("Invoice no-" + item + " is processed as valid - " + result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		long duration = utils.measureTimeInMilis(pocetak);
		System.out.println("Transaction duration for postxml3: " + duration);
		
		return "End of service call - parallel stream of xml strings! Duration: " + duration;
    }
	
	@POST
    @Path("/postxml3-v1")
    public String restPostPureXMLParallelStreamV1(String serverIP) {
		List<String> items = utils.getXMLFilesAsString(serverIP);

		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		LocalDateTime pocetak = LocalDateTime.now();
		
		items.parallelStream().forEach(item->{
			try {
				Document doc = dbf.newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(item.getBytes("utf-8"))));
				String result = integration.processInvoice(doc);
				
				if (StringUtils.equals(result, "true")) {
					insert.InsertIntoInvoiceV1(doc, conn);
		    		//return Response.status(200).entity("Success!").build();
		    	}
				//System.out.println("Invoice no-" + item + " is processed as valid - " + result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		long duration = utils.measureTimeInMilis(pocetak);
		System.out.println("Transaction duration for postxml3: " + duration);
		
		return "End of service call - parallel stream of xml strings! Duration: " + duration;
    }
	
	
	@POST
    @Path("/postdoc")
    public String restPostDocXML() {

		List<Document> items = utils.getXMLFilesAsDoc();
		LocalDateTime pocetak = LocalDateTime.now();
		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		
		items.forEach(item->{
			try {
				String result = integration.processInvoice(item);
				//System.out.println("Invoice no-" + item + " is processed as valid - " + result);
				
				if (StringUtils.equals(result, "true")) {
					insert.InsertIntoInvoice(item, conn);
		    		//return Response.status(200).entity("Success!").build();
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		long duration = utils.measureTimeInMilis(pocetak);
		System.out.println("Transaction duration for postdoc: " + duration);
				
		return "End of service call - sequential stream of document objects! Duration: " + duration;
    }
	
	
	@POST
    @Path("/postdoc-v1")
    public String restPostDocXMLV1() {

		List<Document> items = utils.getXMLFilesAsDoc();
		LocalDateTime pocetak = LocalDateTime.now();
		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		
		items.forEach(item->{
			try {
				String result = integration.processInvoice(item);
				//System.out.println("Invoice no-" + item + " is processed as valid - " + result);
				
				if (StringUtils.equals(result, "true")) {
					insert.InsertIntoInvoiceV1(item, conn);
		    		//return Response.status(200).entity("Success!").build();
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		long duration = utils.measureTimeInMilis(pocetak);
		System.out.println("Transaction duration for postdoc: " + duration);
				
		return "End of service call - sequential stream of document objects! Duration: " + duration;
    }
	
	
	
	@POST
    @Path("/postdoc2")
    public String restPostDocXMLParallel() {

		List<Document> items = utils.getXMLFilesAsDoc();
		LocalDateTime pocetak = LocalDateTime.now();
		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		
		items.parallelStream().forEach(item->{
			try {
				String result = integration.processInvoice(item);
				//System.out.println("Invoice no-" + item + " is processed as valid - " + result);
				
				if (StringUtils.equals(result, "true")) {
					insert.InsertIntoInvoice(item, conn);
		    		//return Response.status(200).entity("Success!").build();
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		long duration = utils.measureTimeInMilis(pocetak);
		//System.out.println("Transaction duration for postdoc2: " + duration);
				
		return "End of service call - parallel stream of document objects! Duration: " + duration;
    }
	
	
	@POST
    @Path("/postdoc2-v1")
    public String restPostDocXMLParallelV1() {

		List<Document> items = utils.getXMLFilesAsDoc();
		LocalDateTime pocetak = LocalDateTime.now();
		InsertInvoice insert = new InsertInvoice();
		Connection conn = insert.prepareConnection();
		
		items.parallelStream().forEach(item->{
			try {
				String result = integration.processInvoice(item);
				//System.out.println("Invoice no-" + item + " is processed as valid - " + result);
				
				if (StringUtils.equals(result, "true")) {
					insert.InsertIntoInvoiceV1(item, conn);
		    		//return Response.status(200).entity("Success!").build();
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		long duration = utils.measureTimeInMilis(pocetak);
		//System.out.println("Transaction duration for postdoc2: " + duration);
				
		return "End of service call - parallel stream of document objects! Duration: " + duration;
    }

}
