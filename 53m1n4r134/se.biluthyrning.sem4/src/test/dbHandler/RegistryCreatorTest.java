package test.dbHandler;

import static org.junit.Assert.*;

import org.junit.Test;

import main.dbHandler.RegistryCreator;

/**
 * This class checks if the registry is created without hickups.
 */
public class RegistryCreatorTest {

	/**
	 * Test method for {@link dbHandler.RegistryCreator#RegistryCreator()}.
	 * Checks if the registry creator is created.
	 */
	@Test
	public void testRegistryCreator() {
		RegistryCreator registryCreator = new RegistryCreator();
		assertNotEquals("No registryCreator available", registryCreator, null);
	}

	/**
	 * Test method for {@link dbHandler.RegistryCreator#getPaymentRegistry()}.
	 * Checks if the payment registry is created.
	 */
	@Test
	public void testGetPaymentRegistry() {
		RegistryCreator registryCreator = new RegistryCreator();
		assertNotEquals("Payment registry is nonexistent", registryCreator.getPaymentRegistry(), null);
	}

	/**
	 * Test method for {@link dbHandler.RegistryCreator#getInspectionRegistry()}.
	 * Checks if the inspection registry is created.
	 */
	@Test
	public void testGetInspectionRegistry() {
		RegistryCreator registryCreator = new RegistryCreator();
		assertNotEquals("Inspection registry is nonexistent", registryCreator.getInspectionRegistry() , null);
	}

}
