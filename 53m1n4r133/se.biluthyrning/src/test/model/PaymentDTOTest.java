/**
 * 
 */
package test.model;

import static org.junit.Assert.*;

import org.junit.Ignore;

import main.model.PaymentDTO;

import org.junit.Test;

/**
 * Test class for the payment DTO.
 */
public class PaymentDTOTest {

	/**
	 * Test method for {@link model.PaymentDTO#paymentDTO(java.lang.Boolean, int, int, int, int, int, java.lang.String)}.
	 */
	@Test
	public void testPaymentDTO() {
		PaymentDTO paymentDTO = createPaymentDTO();
		assertNotEquals("No paymentDTO was created", paymentDTO, null);
	}
/**
 * Test method to make sure that we can't enter <code>cardNumber</code> with incorrect number of digits
 */
	@Ignore
	@Test
	public void testIncorrectNumbersofDigitsForCardNumber()  {
		boolean card = false;
		int amount = 1000;
		String cardNumber = "4854";
		int cvc = 123;
		int expiry = 1018;
		int code = 1421;
		String cardHolder = "Sten Sture";
		int correctCardNumberFormat = 16;
		
		PaymentDTO paymentDTO = new PaymentDTO(card, amount, cardNumber, cvc, expiry, code, cardHolder);
		int cardNumberLength = paymentDTO.getCardNumber().length();
		assertEquals("DTO created with incorrect number of digits for card number", correctCardNumberFormat, cardNumberLength);
	
	}
	/**
	 *  Test method to see if we can retreive the <code>code</code> for the card.
	 */
	@Test
	public void testGetCode() {
		PaymentDTO paymentDTO = createPaymentDTO();
		int code = paymentDTO.getCode();
		assertEquals("The code does not match", code, 1421);
	}
	/**
	 * Test method to see if we can retrieve the <code>amount</code>
	 */
	@Test
	public void testGetAmount() {
		PaymentDTO paymentDTO = createPaymentDTO();
		int amount = paymentDTO.getAmount();
		assertEquals("The amount does not match", amount, 1000);
	}
	/**
	 * Test method to see if we can retrieve the <code>cardNumber</code>
	 */
	@Test
	public void testGetCardNumber() {
		PaymentDTO paymentDTO = createPaymentDTO();
		String cardNumber = paymentDTO.getCardNumber();
		assertEquals("The CardNumber does not match", cardNumber, "4894640214243468");
	}
	
	/**
	 * This method contains the values for the DTO which
	 * we will use to test the different methods related
	 * to the class
	 * @return A DTO for use in the test methods
	 */
	private PaymentDTO createPaymentDTO() {
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
