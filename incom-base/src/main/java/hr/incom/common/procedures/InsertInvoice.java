package hr.incom.common.procedures;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.map.ext.DOMDeserializer.NodeDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hr.incom.shared.helper.RestUtil;


public class InsertInvoice {
	
	RestUtil util = new RestUtil();
	private static InsertInvoice instance;
	
	private Connection conn;
	private static final Logger LOG = LoggerFactory.getLogger(InsertInvoice.class);
	
	public static InsertInvoice getInstance() {
	    if(instance == null) {
	        synchronized(InsertInvoice.class) {
	            if(instance == null) {
	            	instance = new InsertInvoice();
	            }
	        }
	    }
	    return instance;
	}
	
	private InsertInvoice() {
		this.conn = this.prepareConnection();
	}

	public Connection prepareConnection() {
		Connection conn = null;
		LOG.debug("Prepare connection!");
		String URL = "jdbc:oracle:thin:@ed2s-scan.osc.uk.oracle.com:1521/pdb1";
		String USER = "TRN_V2";
		String PASS = "welcome1";
				
		try {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch(ClassNotFoundException ex) {
			   LOG.info("Error: unable to load driver class!");
			   System.exit(1);
			}
		try {
			conn=DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	 }
	
	
	public String InsertIntoInvoice(Document doc, String xml) {
		
		CallableStatement statement = null;
		String responseXml = null;
		Date today = new Date();
		int id;
		
		try {
			statement = conn.prepareCall("{call pr_insert_invoice_data(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			statement.registerOutParameter(1, java.sql.Types.BIGINT);
			statement.setLong(1, 1);
			statement.setString(2,UUID.randomUUID().toString());
			statement.setTimestamp(3, new Timestamp(today.getTime()));
			//statement.setTimestamp(3, convertStringToTimestamp(doc.getElementsByTagName("tns:DatumVrijeme").item(0).getFirstChild().getNodeValue()));
			statement.setTimestamp(4, new Timestamp(today.getTime()));
			statement.setTimestamp(5, new Timestamp(today.getTime()));
			statement.setTimestamp(6, new Timestamp(today.getTime()));
			statement.setString(7, doc.getElementsByTagName("tns:Oib").item(0).getFirstChild().getNodeValue());
			statement.setString(8,null);
			statement.setInt(9, doc.getElementsByTagName("tns:USustPdv").item(0).getFirstChild().getNodeValue().equalsIgnoreCase("true") ? 1 : 0);
	    	statement.setString(10, doc.getElementsByTagName("tns:OznSlijed").item(0).getFirstChild().getNodeValue());
	    	statement.setString(11, doc.getElementsByTagName("tns:BrOznRac").item(0).getFirstChild().getNodeValue());
	    	statement.setString(12, doc.getElementsByTagName("tns:OznPosPr").item(0).getFirstChild().getNodeValue());
	    	statement.setString(13, doc.getElementsByTagName("tns:OznNapUr").item(0).getFirstChild().getNodeValue());
	    	statement.setDouble(14, Double.parseDouble(doc.getElementsByTagName("tns:IznosOslobPdv").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(15, Double.parseDouble(doc.getElementsByTagName("tns:IznosMarza").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(16, Double.parseDouble(doc.getElementsByTagName("tns:IznosNePodlOpor").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(17, Double.parseDouble(doc.getElementsByTagName("tns:IznosUkupno").item(0).getFirstChild().getNodeValue()));
	    	statement.setString(18, "HRK");
	    	statement.setString(19, doc.getElementsByTagName("tns:NacinPlac").item(0).getFirstChild().getNodeValue());
	    	statement.setString(20, doc.getElementsByTagName("tns:OibOper").item(0).getFirstChild().getNodeValue());
	    	statement.setString(21, doc.getElementsByTagName("tns:ZastKod").item(0).getFirstChild().getNodeValue());
	    	statement.setInt(22, doc.getElementsByTagName("tns:NakDost").item(0).getFirstChild().getNodeValue().equalsIgnoreCase("true") ? 1 : 0);
	    	statement.setString(23, doc.getElementsByTagName("tns:ParagonBrRac").item(0).getFirstChild().getNodeValue());
	    	statement.setString(24, doc.getElementsByTagName("tns:SpecNamj").item(0).getFirstChild().getNodeValue());
	    	statement.setString(25,null);
	    	statement.setString(26,null);
	    	statement.setString(27,xml);
	    	
	    	int result = statement.executeUpdate();
	    	
	    	if (result==1) {
	    		id = statement.getInt(1);

		    	NodeList nodeList = doc.getElementsByTagName("tns:Porez").item(0).getChildNodes();
		    	
		    	 for (int i = 0; i < nodeList.getLength(); i++) {
		    	        Node currentNode = nodeList.item(i);
		    	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
		    	            insertIntoInvoiceItems(id, nodeList, conn);		    	            
		    	        }
		    	    }
	    	
		    	 responseXml = util.createResponse(id);
		    	}
	    		else {
	    			LOG.info("Unable to execute procedure! Invoice id: " + statement.getInt(1));
	    		}
	    	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(statement!=null)
					statement.close();
			}
			catch(Exception e) {}
		}
		
		return responseXml;
	}
	
	
public void insertIntoInvoiceItems(int invoiceId, NodeList nodeList, Connection conn) {
	
	CallableStatement statement = null;
	int itemId;
	double stopa = 0.0;
	double osnovica = 0.0;
	double iznos = 0.0;
	
	for (int i = 0; i < nodeList.getLength(); i++) {
        Node currentNode = nodeList.item(i);
        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
        	if (currentNode.getNodeName().equals("tns:Stopa")) {
        		stopa = Double.parseDouble(currentNode.getFirstChild().getNodeValue());
        	}
        	else if (currentNode.getNodeName().equals("tns:Osnovica")) {
        		osnovica = Double.parseDouble(currentNode.getFirstChild().getNodeValue());
        	}
        	else if (currentNode.getNodeName().equals("tns:Iznos")) {
        		iznos = Double.parseDouble(currentNode.getFirstChild().getNodeValue());
        	}
        }
	}
	
	try {
		statement = conn.prepareCall("{call PR_INSERT_INVOICE_ITEMS_DATA(?,?,?,?,?,?)}");
		
		statement.registerOutParameter(1, java.sql.Types.BIGINT);
		statement.setLong(1, 1);
		statement.setDouble(2, invoiceId);		
		statement.setString(3, "Porez");
		statement.setDouble(4, stopa);
		statement.setDouble(5, osnovica);
		statement.setDouble(6, iznos);
    	
    	boolean result = statement.execute();
    	
    	if(result) {
    		itemId = statement.getInt(1);
    	}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		try {
			if(statement!=null)
				statement.close();
		}
		catch(Exception e) {}
	}
}
	
}
