package model;

import java.util.List;

/**
 *	Observer which observes the item list.
 */
public interface ListObserver {

	/**
	 * Updates the lineItem list in the view.
	 * @param lineItems	The list which should be updated.
	 */
	public void notify(List<SalesLineItem> lineItems);
}