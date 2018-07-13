package hr.incom.common.procedures;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.codehaus.jackson.map.ext.DOMDeserializer.NodeDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hr.incom.shared.helper.RestUtil;


public class InsertInvoiceFlat {
	
	RestUtil util = new RestUtil();
	private static InsertInvoiceFlat instance;
	
	private Connection conn;
	private static final Logger LOG = LoggerFactory.getLogger(InsertInvoiceFlat.class);
	
	public static InsertInvoiceFlat getInstance() {
	    if(instance == null) {
	        synchronized(InsertInvoiceFlat.class) {
	            if(instance == null) {
	            	instance = new InsertInvoiceFlat();
	            }
	        }
	    }
	    return instance;
	}
	
	private InsertInvoiceFlat() {
		try {
			this.conn = this.prepareConnectionPool();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*private InsertInvoiceFlat() {
		this.conn = this.prepareConnection();
	}
*/
	public Connection prepareConnection() {
		Connection conn = null;
		LOG.debug("Prepare connection - flat!");
		String URL = "jdbc:oracle:thin:@localhost:1521/ora.incom";
		String USER = "opr";
		String PASS = "Salekoliko1";
		
				
		try {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch(ClassNotFoundException ex) {
			   LOG.debug("Error: unable to load driver class!");
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
	
	public Connection prepareConnectionPool() throws NamingException, SQLException {
		Connection conn = null;
		System.out.println("Prepare connection pool - flat!");
		Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put(Context.INITIAL_CONTEXT_FACTORY,   "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        
		Context initialContext = new InitialContext(ht);
		DataSource ds = (DataSource) initialContext.lookup("localV1");
		conn = ds.getConnection();
	
		return conn;
	 }
	
		
public String InsertIntoInvoice(Document doc, String xml) {
		
		CallableStatement statement = null;
		String responseXml = null;
		Date today = new Date();
		int id;
		
		try {
			statement = this.conn.prepareCall("{call PR_INSERT_INVOICE_DATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			
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
	    	statement.setString(63,xml);
	    	
	    	int result = statement.executeUpdate();

	    	if (result==1) {
	    		responseXml = util.createResponse(statement.getInt(1));
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

}
