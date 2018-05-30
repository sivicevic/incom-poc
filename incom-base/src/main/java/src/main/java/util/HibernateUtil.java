package src.main.java.util;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;

import hr.incom.common.domain.model.Invoice;


public class HibernateUtil
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("incom");

    
    /**
     * Create a new Invoice.
     * 
     */
    public void createInvoice(String oib, int vatObligation, char invoiceSeqIndicator, String paragInvLabel, String paymentMethod, String specPurpose, double totalAmount) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Create a new Invoice object
            Invoice invoice = new Invoice();
            invoice.setDateDBInsert(LocalDateTime.now());
            invoice.setTaxPayerNumber(oib);
            invoice.setVatObligation(vatObligation);
            invoice.setInvoiceSeqIndicator(invoiceSeqIndicator);
            invoice.setParagInvLabel(paragInvLabel);
            invoice.setCurrency("HRK");
            invoice.setPaymentMethod(paymentMethod);
            invoice.setSpecPurpose(specPurpose);
            invoice.setTotalAmount(totalAmount);
            
            
            // Save the invoice object
            manager.persist(invoice);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }
}
