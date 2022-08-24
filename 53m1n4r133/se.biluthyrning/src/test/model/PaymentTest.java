/**
 * 
 */
package test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import main.model.Payment;
import main.model.PaymentDTO;

/**
 *	Tests the payment class.
 */
public class PaymentTest {

	/**
	 * Test method for {@link main.model.Payment#Payment(main.model.PaymentDTO)}.
	 * Tests if we're doing the wrong checks during the payment.
	 */
	@Test
	public void testPayment() {
		PaymentDTO cardPaymentDTO = new PaymentDTO(true, 0, "", 0, 0, 0, "");
		PaymentDTO cashPaymentDTO = new PaymentDTO(false, 0, "", 0, 0, 0, "");
		Payment cardPayment = new Payment(cardPaymentDTO);
		Payment cashPayment = new Payment(cashPaymentDTO);
		assertNotEquals("Checked with payment auth. if cash was authorized.", cashPayment.isPaymentSuccessful(), true);
		assertEquals("Card has change.", cardPayment.getChange(), 0);
	}
}
