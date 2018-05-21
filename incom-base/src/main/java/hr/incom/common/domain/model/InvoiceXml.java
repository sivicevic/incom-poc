package hr.incom.common.domain.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="ICM_CP_INVOICE_XML")
public class InvoiceXml implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Cash Payment Invoice ID FK
	@Id
	@Column(name="INVOICE_CP_ID")
	private long invoiceCpId;
	
	@Column(name="XML_DATA")
	@Lob
	private Blob xmlData;

	public long getInvoiceCpId() {
		return invoiceCpId;
	}

	public void setInvoiceCpId(long invoiceCpId) {
		this.invoiceCpId = invoiceCpId;
	}

	public Blob getXmlData() {
		return xmlData;
	}

	public void setXmlData(Blob xmlData) {
		this.xmlData = xmlData;
	}

}
