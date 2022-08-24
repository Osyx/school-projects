package main.model;

/**
 * 
 * This class encapsulates the payment details provided by the customer, which will then
 * serve as a vessel as it travels across the system.
 *
 */
public class PaymentDTO {
	private boolean card;
	private int amount;
	private String cardNumber;
	private int cvc;
	private int expiry;
	private int code;
	private String cardHolder;
	
	/**
	 * Construtor for the DTO
	 * @param card 			Specifies whether or not the customer is paying with a Credit Card.
	 * @param amount 		The amount that the customer has to pay 
	 * @param cardNumber 	The Card Number of the customer's card
	 * @param cvc 			The CVC code belonging to the card
	 * @param expiry 		The expiry date of the card
	 * @param code 			The code that the customer entered
	 * @param cardHolder	The name of the cardholder
	 */
	public PaymentDTO(Boolean card, int amount, String cardNumber, int cvc, int expiry, int code, String cardHolder){
		this.card = card;
		this.amount = amount;
		this.cardNumber = cardNumber;
		this.cvc = cvc;
		this.expiry = expiry;
		this.code = code;
		this.cardHolder = cardHolder;
	}
	/**
	 * 
	 * @return <code>true</code> whether or not the customer is paying with card
	 * 		   <code>false</code> if the customer is paying with cash
	 */
	public boolean getCard(){
		return this.card;
	}
	/**
	 * 
	 * @return the amount the customer is paying
	 */
	public int getAmount(){
		return this.amount;
	}
	
	/**
	 * 
	 * @return The card number of the customer's card
	 */
	public String getCardNumber(){
		return this.cardNumber;
	}
	/**
	 * 
	 * @return The CVC number belonging to the customer's card
	 */
	public int getCVC(){
		return this.cvc;
	}
	/**
	 * 
	 * @return The expiry date of the customer's card
	 */
	public int getExpiry(){
		return this.expiry;
	}
	/**
	 * 
	 * @return The code for the customer's card
	 */
	public int getCode(){
		return this.code;
	}
	/**
	 * 
	 * @return The name of the cardholder
	 */
	public String getCardHolder(){
		return this.cardHolder;
	}
}
