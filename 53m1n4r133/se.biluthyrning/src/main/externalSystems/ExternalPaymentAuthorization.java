package main.externalSystems;

import main.model.PaymentDTO;

/**
 * This class serves as a pseudo payment authorizer that deems
 * every transaction as correct.
 */
public class ExternalPaymentAuthorization {
	
	/**
	 * 
	 * @param paymentDetails	The details relating to the transaction
	 * @return					<code>true</code> if the system acknowledges the Card Details
	 							<code>false</code> if the the details are incorrect
	 */							
	public static boolean authorizePayment(PaymentDTO paymentDetails) {
		return true;
	}
}
