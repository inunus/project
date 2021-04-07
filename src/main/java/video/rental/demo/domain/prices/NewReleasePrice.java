package video.rental.demo.domain.prices;

import video.rental.demo.domain.Video;

public class NewReleasePrice extends Price {
	@Override
	public int getPriceCode() {
		return Video.NEW_RELEASE;
	}

	@Override
	public double getCharge(int daysRented) {
		return daysRented * 3;
	}

	@Override
	public int getSavingPoint() {
		return 2;
	}
}
