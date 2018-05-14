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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DigitalSignatureUtils
{
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
			doc = dbf.newDocumentBuilder().parse(DigitalSignatureUtils.class.getClassLoader().getResourceAsStream(path));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return doc;
	}

	private static PublicKey readPublicKey(String path)
	{
		PublicKey publicKey = null;

		byte[] keydata = getKeyData(path);
		KeyFactory keyFactory = null;
		try
		{
			keyFactory = KeyFactory.getInstance("RSA");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		CertificateFactory fact = null;
		try
		{
			fact = CertificateFactory.getInstance("X.509");
		}
		catch (CertificateException e1)
		{
			e1.printStackTrace();
		}

		X509Certificate cer = null;
		try
		{
			cer = (X509Certificate) fact.generateCertificate(DigitalSignatureUtils.class.getClassLoader().getResourceAsStream(path));
		}
		catch (CertificateException e1)
		{
			e1.printStackTrace();
		}

		return cer.getPublicKey();
	}

	private static byte[] getKeyData(String path)
	{
		URI uri = null;
		try
		{
			uri = DigitalSignatureUtils.class.getClassLoader().getResource(path).toURI();
		}
		catch (URISyntaxException e1)
		{
			e1.printStackTrace();
		}
		File file = new File(uri);
		byte[] buffer = new byte[(int) file.length()];
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			fis.read(buffer);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return buffer;
	}
}
