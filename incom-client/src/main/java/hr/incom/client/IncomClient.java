package hr.incom.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import hr.incom.shared.helper.RestUtil;



public class IncomClient implements Runnable {
	
	private volatile long ms1;
	private volatile long ms2;
	
	private int j;
	private IncomClient mainInstance;
	private String db;
	private String srv;
	private String xml;
	
	TransformerFactory tf = TransformerFactory.newInstance();
	
	private static final Logger LOG = LoggerFactory.getLogger(IncomClient.class);
	
	public IncomClient() {}
	
	public IncomClient(int j, IncomClient mainInstance, String db, String srv, String xml)
	{
		this.j = j;
		this.mainInstance = mainInstance;
		this.db = db;
		this.srv = srv;
		this.xml = xml;
	}

	public static void main(String[] args) {

		String	db = args[0];
		String	msg = args[1];
		String	server = args[2];
		String	xml = args[3];

		IncomClient instance = new IncomClient();
		
		RestUtil util = new RestUtil();
		
		//LocalDateTime ldt = LocalDateTime.now();
		instance.ms1 = (new Date()).getTime();
		int msgNum = Integer.parseInt(msg);

		for (int i = 1; i <= msgNum; i++) {
			int j = i;
			if (i >= 4001) {
				j = j-4000;
			}
			(new Thread(new IncomClient(j, instance, db, server, xml))).start();
		}
		 	
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("total time ms: " + (instance.ms2 - instance.ms1));
		LOG.info("total time ms: " + (instance.ms2 - instance.ms1));
	}

	public void run() {
		this.handleJob(db, srv, xml);
	}
	
	private void handleJob(String db, String srv, String xml) {
		RestUtil utils = new RestUtil();
		URL url = null;
		String server = "http://odc-vm-40-"+srv+".osc.uk.oracle.com:7001/incom-service/api/fiskal";
		String withXml = "";
		
		if (xml.equals("y")) {
			withXml = "Xml";
		}
		else if (xml.equals("n")) {
			withXml = "NOXml";
		}
			
		try {
			
			Document items = utils.getXMLFileAsDoc(this.j);
			if (!srv.equals("") && !srv.equals("-")) {
				if (db.equals("V1")) {
						url = new URL(server+"/postXmlToFlat"+withXml);
				}
				else if (db.equals("V2")) {
					url = new URL(server+"/postXmlInvoice"+withXml);
				}
				else {
					url = new URL(server+"/postXmlToFlat"+withXml);
				}
			}
			else if (srv.equals("-")) {
				url = new URL("http://localhost:7001/incom-service/api/fiskal/postXmlToFlat"+withXml);
			}
			else {
				url = new URL("http://localhost:7001/incom-service/api/fiskal/postXmlToFlat"+withXml);
			}

			//LOG.info("Url: " + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(items), new StreamResult(writer));
			String input = writer.getBuffer().toString();

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

//			String output;
//			while ((output = br.readLine()) != null) {
//				//System.out.println(output);
//			}

			conn.disconnect();


		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		long ms3 = (new Date()).getTime();
		
		if (ms3 > mainInstance.ms2)
			mainInstance.ms2 = ms3;
	}

}
