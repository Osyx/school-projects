/**
 * 
 */
package test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import main.model.CardPayment;
import main.model.Payment;
import main.model.PaymentDTO;
import main.model.Receipt;

/**
 * Test class for the receipt.
 */
public class ReceiptTest {

	/**
	 * Test method for {@link model.Receipt#Receipt()}.
	 */
	@Test
	public void testReceipt() {
		PaymentDTO paymentDTO = new PaymentDTO(true, 0, "", 0, 0, 0, "");
		Payment payment = new Payment(paymentDTO);
		Receipt receipt = new Receipt(payment);
		assertEquals("Receipt not created", receipt.isReceiptCreated(), true);
	}

	/**
	 * Test method for {@link model.Receipt#Receipt(model.Payment)}.
	 */
	@Test
	public void testReceiptForCard() {
		CardPayment payment = new CardPayment(null);
		Receipt receipt = new Receipt(payment);
		assertEquals("Receipt not created", receipt.isReceiptCreated(), true);
	}

	@Test
	public void testReceiptCreation() {
		Receipt receipt = new Receipt();
		PaymentDTO paymentDTO = new PaymentDTO(true, 0, "", 0, 0, 0, "");
		receipt.createReceipt(paymentDTO);
		assertEquals("Receipt not created", receipt.isReceiptCreated(), true);
	}
}
