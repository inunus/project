package video.rental.demo.domain.videotypes;

import video.rental.demo.domain.Video;

public class VHSType extends VideoType {
	@Override
	public int getVideoType() {
		return Video.VHS;
	}

	@Override
	public int getLateReturnPointPenalty() {
		return 1;
	}

	@Override
	public int getDaysRentedLimit() {
		return 5;
	}
}
