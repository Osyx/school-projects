/**
 * 
 */
package test.model;
import main.model.*;


import static org.junit.Assert.*;
import main.model.CardPayment;

import org.junit.Test;

/**
 * Test class for the card payment.
 */
public class CardPaymentTest {

	/**
	 * Test method for {@link model.CardPayment#CardPayment(model.PaymentDTO)}.
	 */
	@Test
	public void testCardPayment() {
		PaymentDTO paymentDTO = createPaymentDetails();
		CardPayment cardPayment = new CardPayment(paymentDTO);
		assertNotEquals("New CardPayment was not created", cardPayment, null);
		}
		


	/**
	 * Test method for {@link model.CardPayment#getApproval()}.
	 */
	@Test
	public void testGetApproval() {	
		PaymentDTO paymentDTO = createPaymentDetails();
		CardPayment cardPayment = new CardPayment(paymentDTO);
		boolean paymentAuthorized = cardPayment.getApproval();
		assertEquals("Payment was not authorized", paymentAuthorized, true);
	}

	/**
	 * Test method for {@link model.CardPayment#getPaymentDetails()}.
	 */
	@Test
	public void testGetPaymentDetails() {
		PaymentDTO paymentDTO = createPaymentDetails();
		CardPayment cardPayment = new CardPayment(paymentDTO);
		PaymentDTO transactionInformation = cardPayment.getPaymentDetails();
		assertNotEquals("Can not retrieve payment details", transactionInformation, null);
	}
	
	/**
	 * Creates a dummy DTO for use in the different test methods
	 * @return A DTO featuring values usable for tests
	 */
	private PaymentDTO createPaymentDetails() {
		boolean card = false;
		int amount = 1000;
		String cardNumber = "4894640214243468";
		int cvc = 123;
		int expiry = 1018;
		int code = 1421;
		String cardHolder = "Sten Sture";
		
		
		PaymentDTO paymentDTO = new PaymentDTO(card, amount, cardNumber, cvc, expiry, code, cardHolder);
		return paymentDTO;
	}

}
