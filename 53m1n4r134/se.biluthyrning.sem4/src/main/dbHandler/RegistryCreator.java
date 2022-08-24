package main.dbHandler;

/**
 * This class serves as a class creator for the two registers containing payments and inspections
 */
public class RegistryCreator {
	private PaymentRegistry paymentRegistry;
	private InspectionRegistry inspectionRegistry;
	
	
	/**
	 * The constructor creates the two registers
	 */
	public RegistryCreator(){
		this.paymentRegistry = new PaymentRegistry();
		this.inspectionRegistry = new InspectionRegistry();
	}
	
	/**
	 * 
	 * @return The <code>paymentRegistry</code>
	 */
	public PaymentRegistry getPaymentRegistry(){ 
		return paymentRegistry;
	}
	/**
	 * 
	 * @return The <code>The inspectionRegistry</code>
	 */
	public InspectionRegistry getInspectionRegistry(){ 
		return inspectionRegistry;
	}
}
