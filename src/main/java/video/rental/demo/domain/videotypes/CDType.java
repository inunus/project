package video.rental.demo.domain.videotypes;

import video.rental.demo.domain.Video;

public class CDType extends VideoType {
	@Override
	public int getVideoType() {
		return Video.CD;
	}

	@Override
	public int getLateReturnPointPenalty() {
		return 2;
	}

	@Override
	public int getDaysRentedLimit() {
		return 3;
	}
}
