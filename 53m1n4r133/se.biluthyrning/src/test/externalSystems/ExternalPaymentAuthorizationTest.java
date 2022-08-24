/**
 * 
 */
package test.externalSystems;
import main.model.PaymentDTO;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test Class for External Payment Authorization.
 */
public class ExternalPaymentAuthorizationTest {

	/**
	 * Test method to make sure that the card provider accepts and authorizes a payment.
	 */
	@Test
	public void testAuthorizePayment() {
		boolean card = false;
		int amount = 1000;
		String cardNumber = "4854";
		int cvc = 123;
		int expiry = 1018;
		int code = 1421;
		String cardHolder = "Sten Sture";
		
		PaymentDTO paymentDetails = new PaymentDTO(card, amount, cardNumber, cvc, expiry, code, cardHolder);
		boolean authorizationStatus = main.externalSystems.ExternalPaymentAuthorization.authorizePayment(paymentDetails);
		assertEquals("Payment was not authorized by the card provider", authorizationStatus, true);
	
	}

}
