package video.rental.demo.domain;

import java.util.List;

public interface Report {
	public String getReport(String name, List<Rental> rentals);
	public String getReport();
}
