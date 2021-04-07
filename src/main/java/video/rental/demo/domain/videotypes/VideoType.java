package video.rental.demo.domain.videotypes;

public abstract class VideoType {
	public abstract int getVideoType();

	public abstract int getLateReturnPointPenalty();

	public abstract int getDaysRentedLimit();
}
