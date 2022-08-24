/**
 * 
 */
package test.externalSystems;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test class for the External Inspection Database.
 */
public class ExternalInspectionDatabaseTest {

	/**
	 * Test method for getInspectionInstructions. Will test with empty, 6 and 20 numbers/letters.
	 */
	@Test
	public void testGetInspectionInstructions() {
		String regnr = "";
		assertEquals("List not recieved from the database.", main.externalSystems.ExternalInspectionDatabase.getInspectionInstructions(regnr).isEmpty(), false);
		regnr = "ABC123";
		assertEquals("List not recieved from the database.", main.externalSystems.ExternalInspectionDatabase.getInspectionInstructions(regnr).isEmpty(), false);
		regnr = "BLIXTHALKA0123456789";
		assertEquals("List not recieved from the database.", main.externalSystems.ExternalInspectionDatabase.getInspectionInstructions(regnr).isEmpty(), false);
	}

}
