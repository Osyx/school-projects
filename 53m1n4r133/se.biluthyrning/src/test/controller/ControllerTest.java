package test.controller;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import main.controller.Controller;
import main.dbHandler.RegistryCreator;
import main.integration.Garage;
import main.integration.Printer;
import main.model.Inspection;
import main.model.Payment;
import main.model.PaymentDTO;
import main.model.ReportDTO;

/**
 * Test class for the controller.
 */
public class ControllerTest {

	/**
	 * Test method for controller constructor. Will give an error if the controller is not being created.
	 */
	@Test
	public void testController() {
		RegistryCreator registryCreator = new RegistryCreator();
		Printer printer = new Printer();
		Garage garage = new Garage();
		Controller controller = new Controller(registryCreator, printer, garage);
		assertNotEquals("Controller not created", controller, null);
	}

	/**
	 * Test method for handleResult.
	 */
	@Test
	public void testHandleResult() {
		List<String> list = new ArrayList<>();
		RegistryCreator registryCreator = new RegistryCreator();
		Printer printer = new Printer();
		Garage garage = new Garage();
		Controller controller = new Controller(registryCreator, printer, garage);
		ReportDTO reportDTO = new ReportDTO(list);
		assertEquals("Couldn't handle results", controller.handleResult(reportDTO), true);
	}

	/**
	 * Test method for payment.  Tests payment with what would be considered "normal" values.
	 */
	@Test
	public void testPaymentNorm() {
		boolean card = true;
		int amount = 100;
		String cardNumber = "314159265358979323";
		int cvc = 687;
		int expiry = 0707;
		int code = 9435;
		String cardHolder = "Bruce Wayne";
		PaymentDTO paymentTest = new PaymentDTO(card, amount, cardNumber, cvc, expiry, code, cardHolder);	
		Payment payment = new Payment(paymentTest);
		assertNotEquals("Payment is not created.", payment, null);	
	}
	
	/**
	 * Test method for payment.  Tests payment with different negative values.
	 */
	@Test
	public void testPaymentNeg() {
		boolean card = true;
		int amount = -100;
		String cardNumber = "-1230427670045203486";
		int cvc = -930;
		int expiry = -0200;
		int code = -9000;
		String cardHolder = "Peter Parker";
		PaymentDTO paymentTest = new PaymentDTO(card, amount, cardNumber, cvc, expiry, code, cardHolder);	
		Payment payment = new Payment(paymentTest);
		assertNotEquals("Payment is not created.", payment, null);	
	}

	/**
	 * Test method for payment.  Tests payment with letters in the card number.
	 */
	@Test
	public void testPaymentLetters() {
		boolean card = true;
		int amount = 100;
		String cardNumber = "ST4RK1NDU5TR135";
		int cvc = 123;
		int expiry = 0125;
		int code = 9001;
		String cardHolder = "Tony Stark";
		PaymentDTO paymentTest = new PaymentDTO(card, amount, cardNumber, cvc, expiry, code, cardHolder);	
		Payment payment = new Payment(paymentTest);
		assertNotEquals("Payment is not created.", payment, null);	
	}
	
	
	/**
	 * Test method for newInspection with an empty string.
	 */
	@Test
	public void testNewInspectionEmpty() {
		RegistryCreator registryCreator = new RegistryCreator();
		Printer printer = new Printer();
		Garage garage = new Garage();
		Controller controller = new Controller(registryCreator, printer, garage);
		Inspection inspection = controller.newInspection("THIS IS A STRING");
		assertNotEquals("Inspection is not created", inspection, null);	
	}
	
	/**
	 * Test method for newInspection with a long string.
	 */
	@Test
	public void testNewInspectionLong() {
		RegistryCreator registryCreator = new RegistryCreator();
		Printer printer = new Printer();
		Garage garage = new Garage();
		Controller controller = new Controller(registryCreator, printer, garage);
		Inspection inspection = controller.newInspection("THIS TEXT MIGHT BE SEEN AS A VERY LONG STRING. DON´T WORRY!");
		assertNotEquals("Inspection is not created", inspection, null);	
	}
	
	/**
	 * Test method for nextQueueNumber. Will test if the values change and if the door opens.
	 */
	@Test
	public void testNextQueueNumber() {
		RegistryCreator registryCreator = new RegistryCreator();
		Printer printer = new Printer();
		Garage garage = new Garage();
		Controller controller = new Controller(registryCreator, printer, garage);
		controller.nextQueueNumber();
		assertEquals("Not the correct number.", garage.getCurrentNumber(), 1);
		controller.nextQueueNumber();
		assertEquals("Number does not increase.", garage.getCurrentNumber(), 2);
		assertEquals("The garagedoor is closed.", garage.getCurrentState(), true);	
	}

	/**
	 * Test method for {@link main.controller.Controller#closeDoor()}.
	 */
	@Test
	public void testCloseDoor() {
		RegistryCreator registryCreator = new RegistryCreator();
		Printer printer = new Printer();
		Garage garage = new Garage();
		Controller controller = new Controller(registryCreator, printer, garage);
		controller.closeDoor();
		assertEquals("The garagedoor is open.", garage.getCurrentState(), false);
		controller.nextQueueNumber();
		controller.closeDoor();
		assertEquals("The garagedoor isn´t closing.", garage.getCurrentState(), false);
		
		
		
	}

}
