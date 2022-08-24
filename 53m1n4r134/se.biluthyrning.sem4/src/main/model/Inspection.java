package main.model;
import java.util.*;

/**
 * The inspection class contains the Inspection object and functions for creating an inspection.
 */
public class Inspection {
	private int cost = 0;
	private String regnr;
	List <String> inspectionList;
	/**
	 * <code>Inspection</code> will represent the inspection of the car with the costs and things to inspect.
	 * @param regnr is the registration number of the car being inspected.
	 * Constructor for inspection object
	 */
	public Inspection (String regnr) {
		this.setRegnr(regnr);
		setInspectionList(main.externalSystems.ExternalInspectionDatabase.getInspectionInstructions(regnr));
		this.setCost(calculateCost(inspectionList));
	}

	/**
	 *  Calculates the cost of inspection based on the things we need to inspect.
	 *  
	 * @param inspectionList 	List of the things we need to inspect on the car
	 * @return					The total cost of the inspection
	 *
	 */
	private int calculateCost(List <String> inspectionList) {
		int costOfInspection = 0;
		for (int i =0; i < inspectionList.size(); i++) {
			costOfInspection++;
			}
		return costOfInspection;
		
	}
	
	/**
	 * 
	 * @return will return the cost of the inspection.
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * <code>setCost</code>will set the cost of the inspection.
	 * @param cost
	 */
	private void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * 
	 * @return will return the regnumber of the inspection.
	 */
	public String getRegnr() {
		return regnr;
	}
	
	/**
	 * <code>setRegnr</code> will set the regnumber of the inspection.
	 * @param regnr
	 */
	private void setRegnr(String regnr) {
		this.regnr = regnr;
	}

	/**
	 * 
	 * @return will return the Inspection List.
	 */
	public List<String> getInspectionList() {
		return inspectionList;
	}

	/**
	 * <code>setInspectionList</code> will set the new inspection list.
	 * @param inspectionList will return the new inspection list.
	 */
	public void setInspectionList(List<String> inspectionList) {
		this.inspectionList = inspectionList;
	}

}

