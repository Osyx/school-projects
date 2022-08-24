package main.controller;

import main.dbHandler.RegistryCreator;
import main.integration.*;
import main.model.Inspection;
import main.model.InspectionResult;
import main.model.Payment;
import main.model.PaymentDTO;
import main.model.Receipt;
import main.model.ReportDTO;

/**
 * The controller class which handels all calls from the view and converts them into method calls for the model.
 */
public class Controller {
	RegistryCreator registryCreator;
	Printer printer;
	Garage garage;
	
	
	/**
	 * <code>Controller</code> is the constructor for <code>Controller</code>.
	 * @param registryCreator is the creator of the databases.
	 * @param printer is the instance of the printer.
	 * @param garage is the instance of the garage.
	 */
	public Controller(RegistryCreator registryCreator, Printer printer, Garage garage){
		this.registryCreator = registryCreator;
		this.printer = printer;
		this.garage = garage;
		
	}
	
	/**
	 * <code>handleResult</code> will create a new instance of InspectionResult using reportDTO. 
	 * @param reportDTO contains the results of the inspection.
	 */
	public boolean handleResult(ReportDTO reportDTO){
		boolean resultHandeled = false;
		InspectionResult inspectionResult = new InspectionResult(reportDTO);
		printer.print(inspectionResult);
		resultHandeled = printer.isPrinted();
		
		return resultHandeled;
		
	}
	/**
	 * <code>payment</code> will initialize a new payment from
	 * the customer, creating the receipt and print it.
	 * 
	 * @param paymentDetails includes all the necessary information about
	 * the transaction that the customer has provided.
	 */
	public Payment payment(PaymentDTO paymentDetails) {
		Payment payment = new Payment(paymentDetails); 
		Receipt receipt = new Receipt(payment);
		printer.print(receipt);
		return payment;
	}
	
	/**
	 * <code>newInspection</code> will initiate a new inspection.
	 * @param regnr is the registration number of the car.
	 * @return 
	 */
	public Inspection newInspection(String regnr){
		Inspection inspection = new Inspection(regnr);
		return inspection;
	}
	
	/**
	 * <code>nextQueueNumber</code> will make a call to garage to prepare for a new customer.
	 */
	public void nextQueueNumber(){
		garage.nextCustomer();
	}
	
	/**
	 * <code>closeDoor</code> will contact <code>garage</code> and tell it to close the garage door.
	 */
	public void closeDoor(){
		garage.closeDoor();
	}

}
