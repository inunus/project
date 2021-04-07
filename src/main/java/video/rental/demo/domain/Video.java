package video.rental.demo.domain;

import video.rental.demo.domain.prices.ChildrenPrice;
import video.rental.demo.domain.prices.NewReleasePrice;
import video.rental.demo.domain.prices.Price;
import video.rental.demo.domain.prices.RegularPrice;
import video.rental.demo.domain.videotypes.CDType;
import video.rental.demo.domain.videotypes.DVDType;
import video.rental.demo.domain.videotypes.VHSType;
import video.rental.demo.domain.videotypes.VideoType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "VIDEO", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Video {
	@Id
	private String title;
	private Rating videoRating;

	@Transient
	private Price price;

	public static final int REGULAR = 1;
	public static final int NEW_RELEASE = 2;
	public static final int CHILDREN = 3;

	@Transient
	private VideoType type;
	public static final int VHS = 1;
	public static final int CD = 2;
	public static final int DVD = 3;

	private LocalDate registeredDate;
	private boolean rented;

	public Video() {
	} // for hibernate

	public Video(String title, int videoType, int priceCode, Rating videoRating, LocalDate registeredDate) {
		this.title = title;
		this.videoRating = videoRating;
		this.registeredDate = registeredDate;
		this.rented = false;
		this.setVideoType(videoType);
		this.setPriceCode(priceCode);
	}

	public int getPriceCode() {
		return price.getPriceCode();
	}

	public double getCharge(int daysRented) {
		return price.getCharge(daysRented);
	}

	private void setPriceCode(int priceCode) {
		switch (priceCode) {
			case REGULAR:
				price = new RegularPrice();
				break;
			case CHILDREN:
				price = new ChildrenPrice();
				break;
			case NEW_RELEASE:
				price = new NewReleasePrice();
				break;
			default:
				throw new IllegalArgumentException("Incorrect price code.");
		}
	}

	public String getTitle() {
		return title;
	}

	public Rating getVideoRating() {
		return videoRating;
	}

	public boolean isRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	private void setVideoType(int videoType) {
		switch (videoType) {
			case VHS:
				type = new VHSType();
				break;
			case CD:
				type = new CDType();
				break;
			case DVD:
				type = new DVDType();
				break;
			default:
				throw new IllegalArgumentException("Incorrect video type.");
		}
	}

	public int getVideoType() {
		return type.getVideoType();
	}

	public boolean rentFor(Customer customer) {
		if (!isUnderAge(customer)) {
			setRented(true);
			Rental rental = new Rental(this);
			List<Rental> customerRentals = customer.getRentals();
			customerRentals.add(rental);
			customer.setRentals(customerRentals);
			return true;
		} else {
			return false;
		}
	}

	public boolean isUnderAge(Customer customer) {
		// calculate customer's age in years and months

		// parse customer date of birth
		Calendar calDateOfBirth = Calendar.getInstance();
		try {
			calDateOfBirth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(customer.getDateOfBirth().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// get current date
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(new java.util.Date());

		// calculate age different in years and months
		int ageYr = (calNow.get(Calendar.YEAR) - calDateOfBirth.get(Calendar.YEAR));
		int ageMo = (calNow.get(Calendar.MONTH) - calDateOfBirth.get(Calendar.MONTH));

		// decrement age in years if month difference is negative
		if (ageMo < 0) {
			ageYr--;
		}
		int age = ageYr;

		// determine if customer is under legal age for rating
		switch (videoRating) {
		case TWELVE:
			return age < 12;
		case FIFTEEN:
			return age < 15;
		case EIGHTEEN:
			return age < 18;
		default:
			return false;
		}
	}

	int getPoint(int daysRented) {
		int point = price.getSavingPoint();

		if (daysRented > type.getDaysRentedLimit())
			point -= Math.min(point, type.getLateReturnPointPenalty());
		return point;
	}
}
