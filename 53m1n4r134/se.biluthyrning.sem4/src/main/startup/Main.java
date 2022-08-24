package main.startup;

import main.controller.Controller;
import main.dbHandler.RegistryCreator;
import main.integration.*;
import main.view.View;

/**
 * The main class which starts the whole system and keeps it going.
 */
public class Main {
	public static void main(String[] args){
		RegistryCreator registryCreator = new RegistryCreator();
		Printer printer = new Printer();
		Garage garage = new Garage();
		Controller controller = new Controller(registryCreator, printer, garage);
		View view = new View(controller);
		
		
		while(true)
			view.handleUI();
	}
}
