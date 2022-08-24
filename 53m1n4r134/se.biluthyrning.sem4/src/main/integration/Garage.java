package main.integration;

/**
 * Contains the constructor for the object Garage and method calls for handling the door and display.
 */
public class Garage {
	/**
	 * Creates instance of the <code>GarageDoor</code> and the <code>Display</code>.
	 */
	main.integration.Garagedoor garageDoor = new main.integration.Garagedoor();
	main.integration.Display display = new main.integration.Display();
	
	/**
	 * Constructor for the <code>Garage</code>.
	 */
	public Garage(){}
	
	/**
	 * Closes the door by calling <code>garageDoor</code>'s function <code>closeDoor()</code>.
	 */
	
	public void closeDoor(){
		garageDoor.closeDoor();
	}
	
	/**
	 * Prepares for the next customer by opening the door and showing the queue number. 
	 * Does this by calling on <code>garageDoor</code> with <code>openGarageDoor()</code> 
	 * and calling <code>display</code> with the function <code>displayNextNumber</code>.
	 */
	public void nextCustomer(){
		garageDoor.openGarageDoor();
		display.displayNextNumber();
	}
	/**
	 * This method gives the status of the door.
	 * @return <code>True</code> if the door is open, <code>False</code> if it is closed
	 * 		
	 */
	public boolean getCurrentState(){
		return garageDoor.getCurrentState();
	}
	
	/**
	 * This method gives the current number shown on the display
	 * @return the <code>currentNumber</code> of the display.
	 */
	public int getCurrentNumber(){
		return display.getCurrentNumber();
	}
}
