package main.model;

/**
 * Creates the inspection result from an ReportDTO.
 */
public class InspectionResult {
	
	InspectionResult inspectionResult;
	boolean reportDone = false;
	
	/**
	 * <code>InspectionResult</code> will create a printable report. This is mostly for converting 
	 * ReportDTO to something more visual. Currently pseudo-making a PDF.
	 * 
	 * @param reportDTO
	 */
	public InspectionResult(ReportDTO reportDTO){
		System.out.println("Creating the nice pdf report.");
		setInspectionResult(null);
		reportDone = true;
		if(reportDone == true) System.out.println("Report creation done.");
	}
	
	/**
	 * 
	 * @return <code>getInspectionResult</code> will return the inspection result.
	 */
	public InspectionResult getInspectionResult() {
		return inspectionResult;
	}

	/**
	 * <code>setInspectionResult</code> will set the inspection result.
	 * @param inspectionResult is the result of the inspection.
	 */
	private void setInspectionResult(InspectionResult inspectionResult) {
		this.inspectionResult = inspectionResult;
	}
	
	/**
	 * 
	 * @return <code>getReportDone</code> will return the finished report.
	 */
	public boolean getReportDone(){
		return reportDone;
	}

}

