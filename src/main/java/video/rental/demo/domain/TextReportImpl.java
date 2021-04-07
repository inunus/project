package video.rental.demo.domain;

import java.util.List;

public class TextReportImpl implements Report {

	public String getReport()
	{
		
		return null;
	}
	@Override
	public String getReport(String name, List<Rental> rentals) {
		String result = "Customer Report for " + name+ "\n";
		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {
			double eachCharge = each.getCharge();
			int eachPoint = each.getPoint();
			int daysRented = each.getDaysRented();

			result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";

			totalCharge += eachCharge;
			totalPoint += eachPoint;
		}
		
		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

		if (totalPoint >= 10) {
			System.out.println("Congrat! You earned one free coupon");
		}
		if (totalPoint >= 30) {
			System.out.println("Congrat! You earned two free coupon");
		}
		return result;
	}

}
