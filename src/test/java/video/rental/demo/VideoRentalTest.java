package video.rental.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class VideoRentalTest {
	
	GoldenMaster goldenMaster = new GoldenMaster();
	
	@Test
	@EnabledOnOs(OS.WINDOWS)
//	@Disabled
	void checkRunResultsWithGoldenMaster_Window() {
		// Given (Arrange)
		String expected = goldenMaster.getGoldenMaster();
		
		// When (Act)
		String actual = goldenMaster.getRunResult();

		// Then (Assert)
		assertEquals(expected, actual.replaceAll("\r\n", "\n"));
	}
	
	@Test
	@EnabledOnOs({OS.LINUX, OS.MAC})
//	@Disabled
	void checkRunResultsWithGoldenMaster_Linux() {
		// Given (Arrange)
		String expected = goldenMaster.getGoldenMaster();
		
		// When (Act)
		String actual = goldenMaster.getRunResult();

		// Then (Assert)
		assertEquals(expected, actual);
	}
	
	@Test
	@Disabled
	void generateGoldenMaster() {
		// Given (Arrange)
		
		// When (Act)
		goldenMaster.generate();

		// Then (Assert)
		
	}

}
