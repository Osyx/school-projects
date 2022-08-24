package main.model;

/**
 *	This class is representing a receipt and its function is that it creates a receipt from incoming information.
 */
public class Receipt {
	/**
	 * This variable represents whether the receipt was successfully created (<code>true</code>) or not (<code>false</code>).
	 */
	boolean receiptCreated = false;
	
	/**
	 * The constructor class for receipt.
	 */
	public Receipt(){}
	
	/**
	 * Creates a receipt from a payment object.
	 * @param payment Represents the incoming Payment object that we want to convert into a receipt.
	 */
	public Receipt(Payment payment){
		System.out.println("Please wait, creating reciept for cash payment...");
		receiptCreated = true;
	}
	
	/**
	 * Creates a receipt from a CardPayment object.
	 * @param payment Represents the incoming CardPayment object that we want to convert into a receipt.
	 */
	public Receipt(CardPayment payment){
		System.out.println("Please wait, creating reciept for card payment...");
		receiptCreated = true;
	}
	
	/**
	 * Creates a receipt from a PaymentDTO object.
	 * @param paymentDTO Represents the incoming Payment object that we want to convert into a receipt
	 */
	public void createReceipt(PaymentDTO paymentDTO){
		System.out.println("Please wait, creating reciept...");
		receiptCreated = true;
	}
	
	/**
	 * A getter for the class <code>Receipt</code>s variable <code>receiptCreated</code>.
	 * @return <code>true</code> or <code>false</code> depending on if the receipt was successfully created or not.
	 */
	public boolean isReceiptCreated() {
		return receiptCreated;
	}

}
