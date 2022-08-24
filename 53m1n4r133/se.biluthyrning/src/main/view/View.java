package main.view;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.controller.Controller;
import main.model.Inspection;
import main.model.Payment;
import main.model.PaymentDTO;
import main.model.ReportDTO;

/**
 * The view class which handles all of the UI:s function calls.
 */
public class View {
	
	List<String> resultList = new ArrayList<>();
	Controller controller;
	Inspection inspection;
	Scanner userInput = new Scanner(System.in);
	
	public View(Controller controller) {
		this.controller = controller;
	}	
	/**
	 * This method represents the pseudo graphical interface through which the user interacts
	 * with the program.
	 */
	public void handleUI(){
		System.out.println("INITIATING CAR INSPECTION");
		System.out.println("\nTime for a new customer?");
		userInput.nextLine();
		nextQueueNumber();
		
		System.out.println("\nClose garagedoor?");
		userInput.nextLine();
		closeDoor();
		
		System.out.println("\nRegistration Number?");
		inspection = controller.newInspection(userInput.nextLine());
		
		System.out.println("\nPAYMENT");
		startPayment();
		
		System.out.println("\nWhat is the result?");
		handleResult(makeNewReport());
		
		System.out.println();
		
	}
	/**
	 * This method is responsible for letting the system know we are ready for
	 * a new customer, which means the garage door will open and a the number
	 * on the display will increase by one
	 */
	public void nextQueueNumber(){
		controller.nextQueueNumber();
	}
	/**
	 * This method is responsible for closing the garage door
	 */
	public void closeDoor(){
		controller.closeDoor();
	}
	/**
	 * This method is responsible for saving the reportDTO in a report format and then print it.
	 * @param reportDTO The information we want to add to the, to be, printed report.
	 */
	private void handleResult(ReportDTO reportDTO){
		controller.handleResult(reportDTO);
	}
	
	/**
	 * This method is responsible for making the reportDTO and does so by adding instructions one by one
	 * from the the database and then asks the instructor for the result for each one of them.
	 * @return reportDTO The <code>ReportDTO</code> that contains the results for the inspection.
	 */
	private ReportDTO makeNewReport(){
		int listIndex = 0;
		for(int i = inspection.getInspectionList().size() ; i > 0 ; i--){
			resultList.add(inspection.getInspectionList().get(listIndex));
			resultList.add(userInput.nextLine());
			listIndex++;
		}
		System.out.println("Created resultList" + resultList);
		ReportDTO reportDTO = new ReportDTO(resultList);
		return reportDTO;
	}
	
	/**
	 * This method is responsible for starting a new payment from 
	 * the customer. In this case we have created an imaginary customer
	 * which would in the reals system be replaced by the information
	 * provided by the point of sale terminal.
	 */
	private void startPayment(){
		boolean card = true;
		int amount = 100;
		String cardNumber = "314159265358979323";
		int cvc = 687;
		int expiry = 0707;
		int code = 9435;
		String cardHolder = "Bruce Wayne";
		System.out.println("Is this information correct?" + 
							"\nCard: " + card + 
							"\nPayment amount: " + amount + 
							"\nCardnumber: " + cardNumber + 
							"\nCVC: " + cvc + 
							"\nExpiry: " + expiry + 
							"\nCode: " + code +
							"\nCardholder: " + cardHolder + "\n");
		userInput.nextLine();
		
		PaymentDTO paymentInformation = makePaymentDTO(card, amount, cardNumber, cvc, expiry, code, cardHolder);
		Payment payment = controller.payment(paymentInformation);
		if(payment.isPaymentSuccessful() == true)	System.out.println("Payment successful.\n");
		else	System.out.println("Payment unsuccessful.");
	}
	/**
	 * This method creates a data transfer object that can
	 * carry the payment information through the system.
	 * @param card whether or not the customer is paying with a credit card or not
	 * @param amount The amount the customer is paying for the inspection
	 * @param cardNumber the Card Number of the customer's credit card
	 * @param cvc the cvc code beloning to the credit card
	 * @param expiry the expiry date of the credit card
	 * @param code the code for the customer's card
	 * @param cardHolder the name of the owner of the credit card
	 * @return A DTO containing all the information relevant to the payment
	 */
	private PaymentDTO makePaymentDTO(Boolean card, int amount, String cardNumber, int cvc, int expiry, int code, String cardHolder){
		PaymentDTO currentPayment = new PaymentDTO(card, amount, cardNumber, cvc, expiry, code, cardHolder);
		
		return currentPayment;
	}

}
