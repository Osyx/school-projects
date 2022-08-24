package model;

import java.util.Map;
import java.util.HashMap;

/**
 * This class is responsible for all access to the product database.
 */ 
public class ProductCatalog { 
	static ProductCatalog instance = new ProductCatalog();
    private Map<Integer, ProductSpecification> products = new HashMap<Integer, ProductSpecification>();

    /**
     * Fills the catalog with some dummy items.
     */
    private ProductCatalog() {
	products.put(1, new ProductSpecification(1, "low fat milk", 
	   "a very long description, a very long description, a very long description", 10));
	products.put(2, new ProductSpecification(2, "butter", 
	   "a very long description, a very long description, a very long description", 10));
	products.put(3, new ProductSpecification(3, "bread", 
	   "a very long description, a very long description, a very long description", 10));
    }

    /**
     * Search for an item in the product catalog.
     *
     * @param    itemId The item to look for
     * @return          The specification for the found item or null if no item was found.
     */
    public ProductSpecification findSpecification(int itemId) throws itemIdOutOfRangeException{
    	
    	if(itemId > products.size())
    		throw new itemIdOutOfRangeException(itemId);
	    return products.get(itemId);
    }
    
    /**
     * Returns the only instance of this singleton object.
     * @return	The instance of the singleton.
     */
    public static ProductCatalog getInstance() {
    	return instance;
    }
    
    /**
     * Class for the <code>Exception</code> <code>itemIdOutOfRangeException</code> which should
     * be called when user calls for an item that doesn't exist.
     */
    @SuppressWarnings("serial")
	public class itemIdOutOfRangeException extends Exception {
        private int itemId;
        
        /**
         * Contructor for the <code>itemIdOutOfRangeException</code> Exception.
         * @param itemId	the faulty <code>itemId<code> which is out of range.
         */
		public itemIdOutOfRangeException(int itemId) {
            super("There is no item with the number " + itemId + " available.");
            this.itemId = itemId;
        }
		
		public int getItemId() {
			return itemId;
		}
    }
}
