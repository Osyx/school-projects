package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.model.InspectionResult;
import main.model.ReportDTO;

/**
 * Test class for the inspection results.
 */
public class InspectionResultTest {

	/**
	 * Tests whether too small or too big lists will throw an exception. 
	 */
	@Test
	public void test() {
		List<String> report = new ArrayList<>();
		ReportDTO reportDTO = new ReportDTO(report);
		InspectionResult inspectionResult = new InspectionResult(reportDTO);
		assertEquals("The report is not being created with and empty list", inspectionResult.getReportDone(), true);
		
		for(int i = 0; i < 50; i++)	report.add("Test");
		ReportDTO reportDTO2 = new ReportDTO(report);
		InspectionResult inspectionResult2 = new InspectionResult(reportDTO2);
		assertEquals("The report is not being created with a very big list", inspectionResult2.getReportDone(), true);
	}

}
