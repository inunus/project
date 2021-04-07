package video.rental.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class RepositoryDBImpl implements Repository {

	// JPA EntityManager
	private static EntityManager em = PersistenceManager.INSTANCE.getEntityManager();

	@Override
	public Customer findCustomerById(int code) {
		getEm().getTransaction().begin();
		Customer customer = getEm().find(Customer.class, code);
		getEm().getTransaction().commit();
		return customer;
	}

	@Override
	public Video findVideoByTitle(String title) {
		getEm().getTransaction().begin();
		Video video = getEm().find(Video.class, title);
		getEm().getTransaction().commit();
		return video;
	}

	@Override
	public List<Customer> findAllCustomers() {
		TypedQuery<Customer> query = getEm().createQuery("SELECT c FROM Customer c", Customer.class);
		return query.getResultList();
	}

	@Override
	public List<Video> findAllVideos() {
		TypedQuery<Video> query = getEm().createQuery("SELECT c FROM Video c", Video.class);
		return query.getResultList();
	}

	@Override
	public void saveCustomer(Customer customer) {
		try {
			getEm().getTransaction().begin();
			getEm().persist(customer);
			getEm().getTransaction().commit();
		} catch (PersistenceException e) {
			return;
		}
	}

	@Override
	public void saveVideo(Video video) {
		try {
			getEm().getTransaction().begin();
			getEm().persist(video);
			getEm().getTransaction().commit();
		} catch (PersistenceException e) {
			return;
		}
		return;
	}

	static EntityManager getEm() {
		return em;
	}

}
