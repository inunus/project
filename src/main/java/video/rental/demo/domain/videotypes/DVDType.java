package video.rental.demo.domain.videotypes;

import video.rental.demo.domain.Video;

public class DVDType extends VideoType {
	@Override
	public int getVideoType() {
		return Video.DVD;
	}

	@Override
	public int getLateReturnPointPenalty() {
		return 3;
	}

	@Override
	public int getDaysRentedLimit() {
		return 2;
	}
}
