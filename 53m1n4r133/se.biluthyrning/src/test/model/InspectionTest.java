package test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import main.model.Inspection;

/**
 *	Tests the inspection class.
 */
public class InspectionTest {

	@Test
	/**
	 * Tests if the inspection was correctly created. 
	 * This is done by checking if we get an positive value on the cost and if the inspection is created at all. 
	 */
	public void testInspection() {
		int lowestValue = 0;
		for(int i = 9999; i > -1; i--) {
			Inspection inspection = new Inspection("ABC 123");
			assertNotEquals("No inspection created", inspection, null);
			if(inspection.getCost() < lowestValue)
				lowestValue = inspection.getCost();
			if(lowestValue < 0) {
				lowestValue = -1;
				break;
			}
		}
		
		assertNotEquals("Cost wrongly calculated", lowestValue, -1);
	}

	@Test
	/**
	 * Tests if the inspection results are correctly fetched.
	 */
	public void testFetchInspectionList() {
		for(int i = 9999; i > -1; i--){
			Inspection inspection = new Inspection("ABC 123");
			assertEquals("Inspection result contains nothing", inspection.getInspectionList().isEmpty(), false);
		}
	}

}
