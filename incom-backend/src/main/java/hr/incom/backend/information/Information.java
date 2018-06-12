package hr.incom.backend.information;

import javax.ejb.Stateless;

import org.w3c.dom.Document;

import hr.incom.backend.helper.DigitalSignatureGenerate;
import src.main.java.util.HibernateUtil;

@Stateless
public class Information implements IInformation
{
	@Override
    public void prepareInsertFromString(String xmlFilePath) {
        
    	DigitalSignatureGenerate generateSignature = new DigitalSignatureGenerate();
    	HibernateUtil hybernateUtil = new HibernateUtil();
    	Document doc = null;
    	
    	doc = generateSignature.getXmlDocument(xmlFilePath);
    	
    	insertInvoice(hybernateUtil, doc);
    }
	
	@Override
    public void prepareInsertFromDoc(Document doc) {
        
    	HibernateUtil hybernateUtil = new HibernateUtil();
    	
    	insertInvoice(hybernateUtil, doc);
    }

	private void insertInvoice(HibernateUtil hybernateUtil, Document doc) {
		String oib = doc.getElementsByTagName("tns:Oib").item(0).getFirstChild().getNodeValue();
    	int vatObligation = doc.getElementsByTagName("tns:USustPdv").item(0).getFirstChild().getNodeValue().equalsIgnoreCase("true") ? 1 : 0 ;
    	char invoiceSeqIndicator = doc.getElementsByTagName("tns:OznSlijed").item(0).getFirstChild().getNodeValue().charAt(0);
    	String paragInvLabel = doc.getElementsByTagName("tns:ParagonBrRac").item(0).getFirstChild().getNodeValue();
    	String paymentMethod = doc.getElementsByTagName("tns:NacinPlac").item(0).getFirstChild().getNodeValue();
    	String specPurpose = doc.getElementsByTagName("tns:SpecNamj").item(0).getFirstChild().getNodeValue();
    	double totalAmount = Double.parseDouble(doc.getElementsByTagName("tns:IznosUkupno").item(0).getFirstChild().getNodeValue());
    	
    	
    	//TODO: insert invoice into database
    	hybernateUtil.createInvoice(oib, vatObligation, invoiceSeqIndicator, paragInvLabel, paymentMethod, specPurpose, totalAmount);
	}
}
