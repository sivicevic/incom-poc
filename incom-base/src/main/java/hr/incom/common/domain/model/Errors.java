package hr.incom.common.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ICM_CP_XML_ERROR_LIST")
public class Errors implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Unique Identifier of error list. The value is artificially generated as a sequence.
	@Id
	@Column(name="ID")
	private long errorId;
	
	// Cash Payment Invoice ID FK
	@Column(name="INVOICE_INVOICE_CP_ID")
	private long invoiceCpId;
	
	// Error Code
	@Column(name="CODE")
	private String errorCode;
	
	// Error name
	@Column(name="NAME")
	private String errorName;
	
	// Error description
	@Column(name="DESCRIPTION")
	private String errorDescription;

	public long getErrorId() {
		return errorId;
	}

	public void setErrorId(long errorId) {
		this.errorId = errorId;
	}

	public long getInvoiceCpId() {
		return invoiceCpId;
	}

	public void setInvoiceCpId(long invoiceCpId) {
		this.invoiceCpId = invoiceCpId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
