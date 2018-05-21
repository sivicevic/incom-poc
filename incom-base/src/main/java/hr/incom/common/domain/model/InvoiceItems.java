package hr.incom.common.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ICM_CP_INVOICE_ITEMS")
public class InvoiceItems implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Unique Identifier of Cash Payment Invoice Items. The value is artificially generated as a sequence.
	@Id
	@Column(name="ID")
	private long invoiceItemId;
	
	// Cash Payment Invoice ID FK
	@Column(name="INVOICE_CP_ID")
	private long invoiceCpId;
	
	// Code of invoice fee
	@Column(name="FEE_CODE")
	private String feeCode;
	
	// Fee rate %
	@Column(name="FEE_PCT")
	private double feePct;
	
	// Fee Base Amount
	@Column(name="FEE_BASE_AMT")
	private double feeBaseAmount;
	
	// Fee Amount
	@Column(name="FEE_AMT")
	private double feeAmount;

	public long getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(long invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public long getInvoiceCpId() {
		return invoiceCpId;
	}

	public void setInvoiceCpId(long invoiceCpId) {
		this.invoiceCpId = invoiceCpId;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public double getFeePct() {
		return feePct;
	}

	public void setFeePct(double feePct) {
		this.feePct = feePct;
	}

	public double getFeeBaseAmount() {
		return feeBaseAmount;
	}

	public void setFeeBaseAmount(double feeBaseAmount) {
		this.feeBaseAmount = feeBaseAmount;
	}

	public double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}
	
}
