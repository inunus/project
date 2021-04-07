package video.rental.demo;

import java.util.List;

public interface Repository {

	Customer findCustomerById(int code);

	Video findVideoByTitle(String title);

	List<Customer> findAllCustomers();

	List<Video> findAllVideos();

	void saveCustomer(Customer customer);

	void saveVideo(Video video);

}