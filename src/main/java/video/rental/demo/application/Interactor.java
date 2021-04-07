package video.rental.demo.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import video.rental.demo.domain.Customer;
import video.rental.demo.domain.Rating;
import video.rental.demo.domain.Rental;
import video.rental.demo.domain.Report;
import video.rental.demo.domain.Repository;
import video.rental.demo.domain.Video;

public class Interactor {
	private Repository repository;
	private Report report;

	public Interactor(Repository repository, Report report) {
		super();
		this.repository = repository;
		this.report = report;
	}

	public String clearRentals(int customerCode) {
		StringBuilder builder = new StringBuilder();

		Customer foundCustomer = getRepository().findCustomerById(customerCode);

		if (foundCustomer == null) {
			builder.append("No customer found").append("\n");
		} else {
			builder.append("Id: " + foundCustomer.getCode() + "\nName: " + foundCustomer.getName() + "\tRentals: "
					+ foundCustomer.getRentals().size()).append("\n");
			for (Rental rental : foundCustomer.getRentals()) {
				builder.append("\tTitle: " + rental.getVideo().getTitle() + " ");
				builder.append("\tPrice Code: " + rental.getVideo().getPriceCode());
			}
			
			List<Rental> customerRentals = foundCustomer.getRentals();
			for (Iterator<Rental> it = customerRentals.iterator(); it.hasNext();) {
				Rental rental = it.next();
				if (rental.getVideo().isRented()) {
					Video video = rental.returnVideo();
					video.setRented(false);
					getRepository().saveVideo(video);
				}
			}

			List<Rental> rentals = new ArrayList<>();
			foundCustomer.setRentals(rentals);

			getRepository().saveCustomer(foundCustomer);
		}
		return builder.toString();
	}

	public void returnVideo(int customerCode, String videoTitle) {
		Customer foundCustomer = getRepository().findCustomerById(customerCode);
		if (foundCustomer == null)
			return;

		List<Rental> customerRentals = foundCustomer.getRentals();

		for (Iterator<Rental> it = customerRentals.iterator(); it.hasNext();) {
			Rental rental = it.next();
			if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
				Video video = rental.returnVideo();
				video.setRented(false);
				getRepository().saveVideo(video);
				it.remove();
				break;
			}
		}

		getRepository().saveCustomer(foundCustomer);
	}

	public String listVideos() {
		StringBuilder builder = new StringBuilder();

		List<Video> videos = getRepository().findAllVideos();
		for (Video video : videos) {
			builder.append(
					"Video type: " + video.getVideoType() +
							"\tPrice code: " + video.getPriceCode() +
							"\tRating: " + video.getVideoRating() +
							"\tTitle: " + video.getTitle()
			)
					.append("\n");
		}
		return builder.toString();
	}

	public String listCustomers() {
		StringBuilder builder = new StringBuilder();

		List<Customer> customers = getRepository().findAllCustomers();
		for (Customer customer : customers) {
			builder.append("ID: " + customer.getCode() + "\nName: " + customer.getName() + "\tRentals: "
					+ customer.getRentals().size()).append("\n");
			for (Rental rental : customer.getRentals()) {
				builder.append("\tTitle: " + rental.getVideo().getTitle() + " ");
				builder.append("\tPrice Code: " + rental.getVideo().getPriceCode());
				builder.append("\tReturn Status: " + rental.getStatus()).append("\n");
			}
		}
		return builder.toString();
	}

	public String getCustomerReport(int code) {
		StringBuilder builder = new StringBuilder();

		Customer foundCustomer = getRepository().findCustomerById(code);
		if (foundCustomer == null) {
			builder.append("No customer found").append("\n");
		} else {
			String result = report.getReport(foundCustomer.getName(), foundCustomer.getRentals());
			builder.append(result).append("\n");
		}
		return builder.toString();
	}

	public void rentVideo(int code, String videoTitle) {
		Customer foundCustomer = getRepository().findCustomerById(code);
		if (foundCustomer == null)
			return;

		Video foundVideo = getRepository().findVideoByTitle(videoTitle);

		if (foundVideo == null)
			return;

		if (foundVideo.isRented() == true)
			return;

		if(foundCustomer.isAvailableAge(foundVideo.getVideoRating())) {
			foundVideo.setRented(true);
			Rental rental = new Rental(foundVideo);
			
			List<Rental> customerRentals = foundCustomer.getRentals();
			customerRentals.add(rental);
			foundCustomer.setRentals(customerRentals);
			
			getRepository().saveVideo(foundVideo);
			getRepository().saveCustomer(foundCustomer);
		}
		else {
			return;
		}
		/*
		Boolean status = foundVideo.rentFor(foundCustomer);
		if (status == true) {
			getRepository().saveVideo(foundVideo);
			getRepository().saveCustomer(foundCustomer);
		} else {
			return;
		}
		*/
	}
	
	

	public void registerCustomer(String name, int code, String dateOfBirth) {
		Customer customer = new Customer(code, name, LocalDate.parse(dateOfBirth));
		getRepository().saveCustomer(customer);
	}

	public void registerVideo(String title, int videoType, int priceCode, int videoRating) {
		LocalDate registeredDate = LocalDate.now();
		Rating rating;
		if (videoRating == 1) rating = Rating.TWELVE;
		else if (videoRating == 2) rating = Rating.FIFTEEN;
		else if (videoRating == 3) rating = Rating.EIGHTEEN;
		else throw new IllegalArgumentException("No such rating " + videoRating);

		Video video = new Video(title, videoType, priceCode, rating, registeredDate);

		getRepository().saveVideo(video);
	}

	private Repository getRepository() {
		return repository;
	}

}
