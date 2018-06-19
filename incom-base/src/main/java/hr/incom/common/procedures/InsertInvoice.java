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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class InsertInvoice {

	public Connection prepareConnection() {
		Connection conn = null;

		String URL = "jdbc:oracle:thin:@ed2s-scan.osc.uk.oracle.com:1521/pdb1";
		String USER = "SASA";
		String PASS = "welcome1";
		
/*		String URL = "jdbc:oracle:thin:@localhost:1521:ora";
		String USER = "opr";
		String PASS = "Salekoliko1";*/
				
		try {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");
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
	
	
	public void InsertIntoInvoice(Document doc, Connection conn) {
		
		CallableStatement statement = null;
		
		UUID uuid = UUID.randomUUID();
		Date today = new Date();
		int id;
		
		try {
			statement = conn.prepareCall("{call pr_insert_invoice_data(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			statement.registerOutParameter(1, java.sql.Types.BIGINT);
			statement.setLong(1, 1);
			statement.setString(2,uuid.toString());
			statement.setTimestamp(3, null);
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
	    	
	    	boolean result = statement.execute();
	    	
	    	//if(result) {
	    		id = statement.getInt(1);

		    	NodeList nodeList = doc.getElementsByTagName("tns:Porez").item(0).getChildNodes();
		    	
		    	 for (int i = 0; i < nodeList.getLength(); i++) {
		    	        Node currentNode = nodeList.item(i);
		    	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
		    	            insertIntoInvoiceItems(id, nodeList, conn);		    	            
		    	        }
		    	    }
	    	//}

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
	
	
public void InsertIntoInvoiceV1(Document doc, Connection conn) {
		
		CallableStatement statement = null;
		
		UUID uuid = UUID.randomUUID();
		Date today = new Date();
		int id;
		
		try {
			statement = conn.prepareCall("{call PR_INSERT_INVOICE_DATA_V1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			statement.registerOutParameter(1, java.sql.Types.BIGINT);
			statement.setLong(1, 1);
			statement.setString(2,uuid.toString());
			statement.setTimestamp(3, null);
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
	    		    	
	    	statement.setDouble(14, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(15, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(16, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(17, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(18, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(19, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(20, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(21, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(22, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(23, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(24, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(25, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(26, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(27, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(28, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(29, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(30, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(31, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setString(32, doc.getElementsByTagName("tns:Naziv").item(0).getFirstChild().getNodeValue());
	    	statement.setDouble(33, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(34, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(35, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setString(36, doc.getElementsByTagName("tns:Naziv").item(0).getFirstChild().getNodeValue());
	    	statement.setDouble(37, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(38, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(39, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setString(40, doc.getElementsByTagName("tns:Naziv").item(0).getFirstChild().getNodeValue());
	    	statement.setDouble(41, Double.parseDouble(doc.getElementsByTagName("tns:Stopa").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(42, Double.parseDouble(doc.getElementsByTagName("tns:Osnovica").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(43, Double.parseDouble(doc.getElementsByTagName("tns:Iznos").item(0).getFirstChild().getNodeValue()));
	    	statement.setString(44, null);
	    	statement.setDouble(45, 0);
	    	statement.setString(46, null);
	    	statement.setDouble(47, 0);
	    	statement.setString(48, null);
	    	statement.setDouble(49, 0);
	    	
	    	statement.setDouble(50, Double.parseDouble(doc.getElementsByTagName("tns:IznosOslobPdv").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(51, Double.parseDouble(doc.getElementsByTagName("tns:IznosMarza").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(52, Double.parseDouble(doc.getElementsByTagName("tns:IznosNePodlOpor").item(0).getFirstChild().getNodeValue()));
	    	statement.setDouble(53, Double.parseDouble(doc.getElementsByTagName("tns:IznosUkupno").item(0).getFirstChild().getNodeValue()));
	    	statement.setString(54, "HRK");
	    	statement.setString(55, doc.getElementsByTagName("tns:NacinPlac").item(0).getFirstChild().getNodeValue());
	    	statement.setString(56, doc.getElementsByTagName("tns:OibOper").item(0).getFirstChild().getNodeValue());
	    	statement.setString(57, doc.getElementsByTagName("tns:ZastKod").item(0).getFirstChild().getNodeValue());
	    	statement.setInt(58, doc.getElementsByTagName("tns:NakDost").item(0).getFirstChild().getNodeValue().equalsIgnoreCase("true") ? 1 : 0);
	    	statement.setString(59, doc.getElementsByTagName("tns:ParagonBrRac").item(0).getFirstChild().getNodeValue());
	    	statement.setString(60, doc.getElementsByTagName("tns:SpecNamj").item(0).getFirstChild().getNodeValue());
	    	statement.setString(61,null);
	    	statement.setString(62,null);
	    	
	    	boolean result = statement.execute();
	    	
	    	if(result) {
	    		id = statement.getInt(1);
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
	
	
	private static Timestamp convertStringToTimestamp(String time) { 
		Date dateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		
		try {
			dateTime = formatter.parse(time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Timestamp(dateTime.getTime());
	}
	
	
	
}
