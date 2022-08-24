package main.integration;

/**
 * Contains the constructor for the Garagedoor object and relevant methods for opening and closing the door.
 */
class Garagedoor {
	/**
	 * Contains the current status of the garage door.
	 * <code>false</code> if the door is closed and <code>true</code> if the door is open.
	 */
	private boolean open = false;
	
	/**
	 * Set the door's status.
	 * @param open Current status of the door.
	 */
	private void setOpen(boolean open) {
		this.open = open;
	}
	/**
	 * Constructor for <code>Garagedoor</code>.
	 */
	Garagedoor(){}
	
	/**
	 * Closes the door.
	 */
	void closeDoor(){
		setOpen(false);
		System.out.println("Door open = " + getCurrentState());
	}
	/**
	 * Opens the door.
	 */
	void openGarageDoor(){
		setOpen(true);
		System.out.println("Door open = " + getCurrentState());
	}
	/**
	 * Returns the current state of the door.
	 * @return <code>open</code>, which contains the current status of the door.
	 */
	boolean getCurrentState(){
		return open;
	}
}
