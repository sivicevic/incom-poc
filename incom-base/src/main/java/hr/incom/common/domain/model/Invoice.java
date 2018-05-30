package hr.incom.common.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ICM_CP_INVOICE")
public class Invoice implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Unique Identifier of Cash Payment Invoice. The value is artificially generated as a sequence.
	@Id
	@SequenceGenerator(name="invoiceId_generator", sequenceName = "INVOICEID_SEQ", allocationSize=50)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoiceId_generator")
	@Column(name="INVOICE_CP_ID", updatable = false, nullable = false)
	private long invoiceId;
	
	// UUID - Universally unique identifier
	@Column(name="UUID_CP_INV")
	private String uuidInvoice;
	
	// Date invoice issued
	@Column(name="DATE_ISSUED")
	private Date dateIssued;
	
	// Date and time of receiving XML message ( dd.mm.yyyy hh24:mi:ss:msec - level milisecond )
	@Column(name="DATE_RECEIVED")
	private LocalDateTime dateReceived;
	
	// Date and time of responce of  XML message ( dd.mm.yyyy hh24:mi:ss:msec - level milisecond )
	@Column(name="DATE_RESPONCE")
	private LocalDateTime dateResponce;
	
	// Date and time od DB insert ( dd.mm.yyyy hh24:mi:ss:msec - level milisecond )
	@Column(name="DATE_DB_INSERT")
	private LocalDateTime dateDBInsert;
	
	// Taxpayer account
	@Column(name="TXPYR_NUMBER")
	private String taxPayerNumber;
	
	// Customer number
	@Column(name="BUYER_TPR_NUMBER")
	private String buyerTprNumber;
	
	// VAT obligation (number 0 or 1)
	@Column(name="VAT_OBLIG")
	private int vatObligation;
	
	// Invoice number sequence indicator
	@Column(name="INVOICE_SEQ_IND")
	private char invoiceSeqIndicator;
	
	// Invoice number index
	@Column(name="INVOICE_NO_IND")
	private String invoiceNumberIndex;
	
	// Business space tag
	@Column(name="TXPYR_BUS_VEN_CODE")
	private String taxPayerCode;
	
	// Cash register ID
	@Column(name="TXPYR_CASH_REGISTER")
	private String taxPayerCashRegCode;
	
	// EXAMPTION_AMOUNT
	@Column(name="EXAMPTION_AMOUNT")
	private double examptionAmount;
	
	// SPECIAL_MARGIN_AMT
	@Column(name="SPECIAL_MARGIN_AMT")
	private double specialMarginAmount;
	
	// NON_TAX_AMT
	@Column(name="NON_TAX_AMT")
	private double nonTaxAmount;
	
	// TOTAL_AMOUNT
	@Column(name="TOTAL_AMOUNT")
	private double totalAmount;
	
	// Currency
	@Column(name="CURERENCY")
	private String currency;
	
	// Payment method
	@Column(name="PAY_METHOD")
	private String paymentMethod;
	
	// Operator taxpayer account
	@Column(name="OPERATOR_TXPYR_NUMBER")
	private String operatorTaxPayerNo;
	
	// Issuer Protection Code
	@Column(name="ISS_PROTECT_CODE")
	private String issuerProtectionCode;
	
	// Post-Delivery Check Mark
	@Column(name="POST_DELIVERY_CHECK_MARK")
	private int postDeliveryChkMark;
	
	// PARAG_INV_LABEL
	@Column(name="PARAG_INV_LABEL")
	private String paragInvLabel;
	
	// For special needs...
	@Column(name="SPEC_PURPOSE")
	private String specPurpose;
	
	// Return unique CP Invoice Identifier
	@Column(name="UII")
	private String uii;
	
	// Has errors in ICM_CP_XML_ERROR_LIST table
	@Column(name="HAS_ERRORS")
	private String hasErrors;
	
	

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getUuidInvoice() {
		return uuidInvoice;
	}

	public void setUuidInvoice(String uuidInvoice) {
		this.uuidInvoice = uuidInvoice;
	}

	public Date getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	public LocalDateTime getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(LocalDateTime dateReceived) {
		this.dateReceived = dateReceived;
	}

	public LocalDateTime getDateResponce() {
		return dateResponce;
	}

	public void setDateResponce(LocalDateTime dateResponce) {
		this.dateResponce = dateResponce;
	}

	public LocalDateTime getDateDBInsert() {
		return dateDBInsert;
	}

	public void setDateDBInsert(LocalDateTime localDateTime) {
		this.dateDBInsert = localDateTime;
	}

	public String getTaxPayerNumber() {
		return taxPayerNumber;
	}

	public void setTaxPayerNumber(String taxPayerNumber) {
		this.taxPayerNumber = taxPayerNumber;
	}

	public String getBuyerTprNumber() {
		return buyerTprNumber;
	}

	public void setBuyerTprNumber(String buyerTprNumber) {
		this.buyerTprNumber = buyerTprNumber;
	}

	public int getVatObligation() {
		return vatObligation;
	}

	public void setVatObligation(int vatObligation) {
		this.vatObligation = vatObligation;
	}

	public char getInvoiceSeqIndicator() {
		return invoiceSeqIndicator;
	}

	public void setInvoiceSeqIndicator(char invoiceSeqIndicator) {
		this.invoiceSeqIndicator = invoiceSeqIndicator;
	}

	public String getInvoiceNumberIndex() {
		return invoiceNumberIndex;
	}

	public void setInvoiceNumberIndex(String invoiceNumberIndex) {
		this.invoiceNumberIndex = invoiceNumberIndex;
	}

	public String getTaxPayerCode() {
		return taxPayerCode;
	}

	public void setTaxPayerCode(String taxPayerCode) {
		this.taxPayerCode = taxPayerCode;
	}

	public String getTaxPayerCashRegCode() {
		return taxPayerCashRegCode;
	}

	public void setTaxPayerCashRegCode(String taxPayerCashRegCode) {
		this.taxPayerCashRegCode = taxPayerCashRegCode;
	}

	public double getExamptionAmount() {
		return examptionAmount;
	}

	public void setExamptionAmount(double examptionAmount) {
		this.examptionAmount = examptionAmount;
	}

	public double getSpecialMarginAmount() {
		return specialMarginAmount;
	}

	public void setSpecialMarginAmount(double specialMarginAmount) {
		this.specialMarginAmount = specialMarginAmount;
	}

	public double getNonTaxAmount() {
		return nonTaxAmount;
	}

	public void setNonTaxAmount(double nonTaxAmount) {
		this.nonTaxAmount = nonTaxAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOperatorTaxPayerNo() {
		return operatorTaxPayerNo;
	}

	public void setOperatorTaxPayerNo(String operatorTaxPayerNo) {
		this.operatorTaxPayerNo = operatorTaxPayerNo;
	}

	public String getIssuerProtectionCode() {
		return issuerProtectionCode;
	}

	public void setIssuerProtectionCode(String issuerProtectionCode) {
		this.issuerProtectionCode = issuerProtectionCode;
	}

	public int getPostDeliveryChkMark() {
		return postDeliveryChkMark;
	}

	public void setPostDeliveryChkMark(int postDeliveryChkMark) {
		this.postDeliveryChkMark = postDeliveryChkMark;
	}

	public String getParagInvLabel() {
		return paragInvLabel;
	}

	public void setParagInvLabel(String paragInvLabel) {
		this.paragInvLabel = paragInvLabel;
	}

	public String getSpecPurpose() {
		return specPurpose;
	}

	public void setSpecPurpose(String specPurpose) {
		this.specPurpose = specPurpose;
	}

	public String getUii() {
		return uii;
	}

	public void setUii(String uii) {
		this.uii = uii;
	}

	public String getHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(String hasErrors) {
		this.hasErrors = hasErrors;
	}
	
	
	
}
