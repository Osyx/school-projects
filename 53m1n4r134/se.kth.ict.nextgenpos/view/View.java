package view;

import java.util.List;

import controller.Controller;
import model.ListObserver;
import model.ProductCatalog.itemIdOutOfRangeException;
import model.SalesLineItem;

/**
 * A placeholder for the view.
 */
public class View {
    private Controller cont;
    private List<SalesLineItem> lineItems;
	private ListObserver observer;

    /**
     * Creates a new <code>View</code>.
     * @param cont           The controller of the application.
     */
    public View(Controller cont) {
		this.cont = cont;
		this.observer = new ListObserver() {

			@Override
			public void notify(List<SalesLineItem> lineItems) {
				viewList(lineItems);
			}
		};
	}

    /**
     * Simulates a view. Makes some calls to the controller.
     */
    public void test() {
		cont.makeNewSale();
		cont.addObserver(this.observer);
		enterItem(1);
		enterItem(2);
		enterItem(10);
    }

    private void enterItem(int itemId) {
		int quantity = 1;
		System.out.println("");
        System.out.println("Your basket contains:\n" + lineItems);
        System.out.println("");
		try {
			System.out.println("Added item " + itemId + ": \n" + cont.enterItem(itemId, quantity));
		} catch (itemIdOutOfRangeException itemIdNotFoundException) {
			System.out.println("There is no item with an item ID of \"" + itemIdNotFoundException.getItemId() + "\", please try again.");
		}
		System.out.println("");
   }
    
	private void viewList(List<SalesLineItem> lineItems) {
		this.lineItems = lineItems;
		
		}
}
