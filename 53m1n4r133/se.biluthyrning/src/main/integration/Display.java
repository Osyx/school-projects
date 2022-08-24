package main.integration;

/**
 * Contains the constructor for the Display and relevant methods for increasing the number on the display. 
 */
class Display {
	/**
	 * <code>currentNumber</code> contains the value shown on the display.
	 */
	private int currentNumber = 0;
	
	/**
	 * Constructor for <code>Display</code>.
	 */
	Display(){}
	
	/**
	 * Displays the next number on the <code>Display</code>.
	 */
	void displayNextNumber(){
		if (++currentNumber > 999)
			currentNumber = 1;
		System.out.println("Current number: " + currentNumber);
	}
	/*
	 * Get the <code>currentNumber</code> the display is showing.
	 */
	int getCurrentNumber(){
		return currentNumber;
	}
} 