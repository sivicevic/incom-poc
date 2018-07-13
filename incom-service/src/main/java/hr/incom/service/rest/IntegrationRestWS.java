package hr.incom.service.rest;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import hr.incom.backend.integration.ejb.Integration;
import hr.incom.common.procedures.InsertInvoice;
import hr.incom.common.procedures.InsertInvoiceFlat;
import hr.incom.shared.helper.RestUtil;

@Path("/fiskal")
public class IntegrationRestWS implements IIntegrationRestWS {
	
	private static final Logger LOG = LoggerFactory.getLogger(RestUtil.class);
	RestUtil utils = new RestUtil();

	Integration integration = new Integration();
	// private static final Logger LOG =
	// LoggerFactory.getLogger(IntegrationRestWS.class);

	@GET
	@Path("/send/{number}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public long restBulkRequestTest(@PathParam("number") int number) {
		long transactionTime = 0;
		for (int i = 1; i <= number; i++) {
			transactionTime = transactionTime + utils.processMessage("xml", i);
		}
		System.out.println(transactionTime);
		LOG.info("Transaction time:" + transactionTime);
		return transactionTime;
	}

	@GET
	@Path("/create/{number}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public long restCreateXmlTest(@PathParam("number") int number) {
		long transactionTime = 0;
		for (int i = 1; i <= number; i++) {
			utils.createInvoices("xml", i);
		}
		System.out.println(transactionTime);
		LOG.info("Transaction time:" + transactionTime);
		return transactionTime;
	}

	@POST
	@Path("/postXmlToFlatXml")
	public String restPostXMLFlatXml(String xml) throws NamingException, SQLException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		InsertInvoiceFlat insertFlat = InsertInvoiceFlat.getInstance();
		long transactionTime = 0;
		LocalDateTime pocetak = LocalDateTime.now();
		String jir = "";
		try {
			Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
			String result = integration.processInvoice(doc);
			//LOG.info("After process invoice" + result);
			
			if (StringUtils.equals(result, "true")) {
				LOG.info("Prepare to insert into DB...");
				String responseFile = insertFlat.InsertIntoInvoice(doc, xml);

				if (!responseFile.equals("")) {
					LOG.info("****Prepare JIR for: " + responseFile);
					jir = utils.validateResponseXml(responseFile);
					LOG.info("JIR: " + jir);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transactionTime = utils.measureTimeInMilis(pocetak);
		LOG.info("Transaction time: " + transactionTime + "ms");

		LOG.info("***** End of service call for flat invoice! JIR: "+jir+" *****");
		return "End of service call for flat invoice! JIR: " + jir;
	}
	
	@POST
	@Path("/postXmlToFlatNOXml")
	public String restPostXMLFlatNOXml(String xml) throws NamingException, SQLException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		InsertInvoiceFlat insertFlat = InsertInvoiceFlat.getInstance();
		long transactionTime = 0;
		LocalDateTime pocetak = LocalDateTime.now();
		String jir = "";
		try {
			Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
			String result = integration.processInvoice(doc);
			//LOG.info("After process invoice" + result);

			if (StringUtils.equals(result, "true")) {
				LOG.info("Prepare to insert into DB...");
				String responseFile = insertFlat.InsertIntoInvoice(doc, null);

				if (!responseFile.equals("")) {
					LOG.info("****Prepare JIR for: " + responseFile);
					jir = utils.validateResponseXml(responseFile);
					LOG.info("JIR: " + jir);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transactionTime = utils.measureTimeInMilis(pocetak);
		LOG.info("Transaction time: " + transactionTime + "ms");
		
		LOG.info("***** End of service call for flat invoice! JIR: "+jir+" *****");
		return "End of service call for flat invoice! JIR: " + jir;
	}

	
	@POST
	@Path("/postXmlInvoiceXml")
	public String restPostXMLInvoiceXml(String xml) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		InsertInvoice insert = InsertInvoice.getInstance();
		long transactionTime = 0;
		LocalDateTime pocetak = LocalDateTime.now();
		String jir = "";
		try {
			Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
			String result = integration.processInvoice(doc);
			//LOG.info("After process invoice" + result);

			if (StringUtils.equals(result, "true")) {
				LOG.info("Prepare to insert into DB...");
				String responseFile = insert.InsertIntoInvoice(doc, xml);

				if (!responseFile.equals("")) {
					LOG.info("****Prepare JIR for: " + responseFile);
					jir = utils.validateResponseXml(responseFile);
					LOG.info("JIR: " + jir);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transactionTime = utils.measureTimeInMilis(pocetak);
		LOG.info("Transaction time: " + transactionTime + "ms");
		
		LOG.info("***** End of service call for invoice and invoice items! JIR: "+jir+" *****");
		return "End of service call for invoice and invoice items!" + jir;
	}
	
	@POST
	@Path("/postXmlInvoiceNOXml")
	public String restPostXMLInvoiceNOXml(String xml) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		InsertInvoice insert = InsertInvoice.getInstance();
		long transactionTime = 0;
		LocalDateTime pocetak = LocalDateTime.now();
		String jir = "";
		try {
			Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
			String result = integration.processInvoice(doc);
			//LOG.info("After process invoice" + result);

			if (StringUtils.equals(result, "true")) {
				LOG.info("Prepare to insert into DB...");
				String responseFile = insert.InsertIntoInvoice(doc, null);

				if (!responseFile.equals("")) {
					LOG.info("****Prepare JIR for: " + responseFile);
					jir = utils.validateResponseXml(responseFile);
					LOG.info("JIR: " + jir);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transactionTime = utils.measureTimeInMilis(pocetak);
		LOG.info("Transaction time: " + transactionTime + "ms");
		
		LOG.info("***** End of service call for invoice and invoice items! JIR: "+jir+" *****");
		return "End of service call for invoice and invoice items!" + jir;
	}

}
