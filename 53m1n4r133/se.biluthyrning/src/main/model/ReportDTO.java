package main.model;
import java.util.*;
 /**
  * 
  * This class serves serves the purpose of encapsulating the content in 
  * which shall serve as the basisfor  <code>InstructionResult</code>
  *
  */
public class ReportDTO{
	/**
	 * Contains a list of element, later in the program filled with the results.
	 */
	List<String> reportList = new ArrayList<>();
 	
	/**
	 * Constructor for <code>ReportDTO</code>.
	 * @param resultList The results we want to save into the <code>ReportDTO</code>
	 */
 	public ReportDTO(List<String> resultList){
 		this.reportList = resultList;
 	}
 	
 	/**
 	 * Getter for <code>reportList</code>
 	 * @return reportList
 	 */
 	public List<String> getReportList(){
 		return this.reportList;
 	}
 
 }