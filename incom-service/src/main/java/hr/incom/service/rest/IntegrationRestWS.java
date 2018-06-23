package hr.incom.service.rest;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;

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
		return transactionTime;
	}

	@POST
	@Path("/postXmlToFlat")
	public String restPostXMLFlat(String xml) throws NamingException, SQLException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		InsertInvoiceFlat insertFlat = InsertInvoiceFlat.getInstance();

		try {
			Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
			String result = integration.processInvoice(doc);

			if (StringUtils.equals(result, "true")) {
				String responseFile = insertFlat.InsertIntoInvoice(doc, xml);

				if (!responseFile.equals("")) {
					String jir = utils.validateResponseXml(responseFile);
					// LOG.info(jir);
					System.out.println(jir);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// LOG.info("End of service call for flat invoice!");
		return "End of service call for flat invoice!";
	}

	@POST
	@Path("/postXmlInvoice")
	public String restPostXMLInvoice(String xml) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		InsertInvoice insert = InsertInvoice.getInstance();

		try {
			Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
			String result = integration.processInvoice(doc);
			System.out.println("After process invoice" + result);

			if (StringUtils.equals(result, "true")) {
				String responseFile = insert.InsertIntoInvoice(doc, xml);

				if (!responseFile.equals("")) {
					String jir = utils.validateResponseXml(responseFile);
					// LOG.info(jir);
					System.out.println(jir);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// LOG.info("End of service call for flat invoice!");
		return "End of service call for invoice and invoice items!";
	}

}
