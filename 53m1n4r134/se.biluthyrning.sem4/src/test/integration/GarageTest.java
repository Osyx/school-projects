/**
 * 
 */
package test.integration;

import static org.junit.Assert.*;

import org.junit.Test;

import main.integration.Garage;

/**
 * This class tests if the garage is working, which includes the display and the garage door.
 */
public class GarageTest {

	/**
	 * Test method for {@link integration.Garage#Garage()}.
	 * Tests if the constructor for <code>Garage</code> works.
	 */
	@Test
	public void testGarage() {
		Garage garage = new Garage();
		assertNotEquals("Garage not created", garage, null);
	}

	/**
	 * Test method for {@link integration.Garage#closeDoor()}.
	 * Tests if the <code>closeDoor()</code> function actually closes the door.
	 */
	@Test
	public void testCloseDoor() {
		Garage garage = new Garage();
		garage.closeDoor();
		assertEquals("Wrong state of door.", garage.getCurrentState(), false);
	}

	/**
	 * Test method for {@link integration.Garage#nextCustomer()}.
	 * Tests if you can get ready for the next customer. 
	 * This is done by checking if the queue number is increased and the door is opened.
	 */
	@Test
	public void testNextCustomer() {
		Garage garage = new Garage();
		for(int i = 0; i < 1000; i++)	garage.nextCustomer();
		
		assertEquals("Wrong state of door.", garage.getCurrentState(), true);
		assertEquals("Wrong number conversion",garage.getCurrentNumber(), 1);
	}

}
