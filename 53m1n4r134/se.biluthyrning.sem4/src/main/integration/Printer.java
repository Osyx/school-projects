package main.integration;

import main.model.InspectionResult;
import main.model.Receipt;

/**
 * Contains the constructor for the object Printer and also the methods for printing.
 */
public class Printer {
	boolean printed = false;
	/**
	 * Constructor for <code>Printer</code>.
	 */
	public Printer(){}
	
	/**
	 * Sends the argument to the printer.
	 * @param obj the object of type <code>InspectionResult</code> we want to print.
	 */
	public void print(InspectionResult obj){
		printed = false;
		System.out.println("Printing result");
		printed = true;
	}
	/**
	 * Sends the argument to the printer.
	 * @param obj the object of the type <code>Receipt</code> we want to print.
	 */
	public void print(Receipt obj){
		printed = false;
		System.out.println("Printing receipt");
		printed = true;
	}
	
	/**
	 * 
	 * @return Whether or not the document has been printed
	 */
	public boolean isPrinted() {
		return printed;
	}
}
