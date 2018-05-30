package hr.incom.backend.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DigitalSignatureVerify
{
	 /**
     * Method used to get the XML document object by parsing xml file
     * @param xmlFilePath
     * @return 
     */
    private static Document getXmlDocument(String xmlFilePath) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            doc = dbf.newDocumentBuilder().parse(new FileInputStream(xmlFilePath));
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return doc;
    }

    /**
     * Method used to verify the XML digital signature
     * @param signedXmlFilePath
     * @param pubicKeyFilePath
     * @return true or false
     * @throws Exception 
     */
    public static boolean isValid(String signedXmlFilePath, 
            String pubicKeyFilePath) throws Exception {
        boolean validFlag = false;
        Document doc = getXmlDocument(signedXmlFilePath);
        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("No XML Digital Signature Found, document is discarded");
        }
        PublicKey publicKey = new DigitalSignatureUtil().getStoredPublicKey(pubicKeyFilePath);
        DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);
	validFlag = signature.validate(valContext);
        return validFlag;
    }
    
    
    public static boolean isValid(Document doc) throws Exception
	{
		boolean valid = false;

		NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
		if (nl.getLength() == 0)
			throw new Exception("No XML Digital Signature Found, document is discarded");

		DOMValidateContext context = new DOMValidateContext(new X509KeySelector(), nl.item(0));
		context.setIdAttributeNS((Element) nl.item(0).getParentNode(), null, "Id");
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		XMLSignature signature = fac.unmarshalXMLSignature(context);
		valid = signature.validate(context);

		return valid;
	}

	public static Document readDocument(String path)
	{
		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try
		{
			doc = dbf.newDocumentBuilder().parse(DigitalSignatureVerify.class.getClassLoader().getResourceAsStream(path));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return doc;
	}
}
