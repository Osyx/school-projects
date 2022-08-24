/**
 * 
 */
package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.model.ReportDTO;

/**
 * Test class for the report DTO.
 */
public class ReportDTOTest {

	/**
	 * Test method for {@link model.ReportDTO#ReportDTO(java.util.List)}.
	 */
	@Test
	public void testReportDTO() {
		List<String> reportList = new ArrayList<>();
		reportList.add("Not empty");
		ReportDTO reportDTO = new ReportDTO(reportList);
		assertNotEquals("ReportDTO not created", reportDTO, null);
		
		assertNotEquals("ReportDTO doesn't contains a reportList", reportDTO.getReportList().isEmpty(), true);
	}

}
