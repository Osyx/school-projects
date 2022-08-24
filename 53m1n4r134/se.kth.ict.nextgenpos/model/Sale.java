package model;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a single sale to one customer.
 */
public class Sale {
    private List<SalesLineItem> lineItems;
    private List<ListObserver> observers = new ArrayList<ListObserver>();
    private int currentTotal;
    private int payedAmount;
    private int iterator;

    /**
     * Instantiates a new <code>Sale</code>.
     */
    public Sale() {
    	lineItems = new ArrayList<SalesLineItem>();
    	
    }

    /**
     * Adds new items to the current <code>Sale</code>. 
     *
     * @param spec            The specification of the items that is added.
     * @param quantity        The number of items.
     */
    public void addItem(ProductSpecification spec, int quantity) {
		SalesLineItem lineItem = new SalesLineItem(spec, quantity);
		lineItems.add(lineItem);
		addToTotal(lineItem);
		notifyObservers();
    }

    private void addToTotal(SalesLineItem lineItem) {
    	currentTotal = currentTotal + lineItem.getCost();
    }
    
    /**
     * Registers an Observer that shall be notified about changes in the 
     * users basket (<code>lineItem</code> list.)
     *
     * @param observers The Observer that should be registered.
     */ 
    public void addObserver(ListObserver observer) {
    	observers.add(observer);
    }

    private void notifyObservers() {
		for (ListObserver observer : observers) {
		    observer.notify(lineItems);
		}
    }

    /**
     * Returns the total cost of all products registered so for.
     *
     * @return The total cost of all products registered so for.
     */
    public int getCurrentTotal() {
    	return currentTotal;
    }

    /**
     * Calculates change and returns all information needed for the receipt.
     *
     * @return All information needed for the receipt.
     */
    public Receipt createReceipt(int payedAmount) {
    	this.payedAmount = payedAmount;
		return new Receipt(this);
    }

    void resetLineItemIterator() {
    	iterator = 0;
    }

    SalesLineItem nextLineItem() {
    	return lineItems.get(iterator);
    }

    boolean hasMoreLineItems() {
    	return iterator < lineItems.size();
    }

    int getPayedAmount() {
    	return payedAmount;
    }
    
}
