package startup;

import controller.Controller;
import view.View;

/**
 * Starts the application.
 */
public class Main {
    public static void main(String[] args) {
	Controller cont = new Controller();
	View view = new View(cont);
	view.test();
    }
}
