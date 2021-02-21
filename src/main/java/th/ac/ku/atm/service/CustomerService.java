package th.ac.ku.atm.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import th.ac.ku.atm.data.CustomerRepository;
import th.ac.ku.atm.model.Customer;

@Service
public class CustomerService {

//	private static List<Customer> customerList;

	private CustomerRepository repository;

//	@PostConstruct
//	public void postConstruct() {
//		if (CustomerService.customerList == null)
//			CustomerService.customerList = new ArrayList<>();
//	}

	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public void createCustomer(Customer customer) {
		String hashPin = hash(customer.getPin());
		customer.setPin(hashPin);
//		customerList.add(customer);
		repository.save(customer);
	}

	public List<Customer> getCustomers() {
//		return new ArrayList<>(CustomerService.customerList);
		return repository.findAll();
	}

	private String hash(String pin) {
		String salt = BCrypt.gensalt(12);
		return BCrypt.hashpw(pin, salt);
	}

	public Customer findCustomer(int id) {
//		for (Customer customer : customerList) {
//			if (customer.getId() == id)
//				return customer;
//		}
//		return null;
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}

	}

	public Customer checkPin(Customer inputCustomer) {
		Customer storedCustomer = findCustomer(inputCustomer.getId());
		if (storedCustomer != null) {
			String hashPin = storedCustomer.getPin();

			if (BCrypt.checkpw(inputCustomer.getPin(), hashPin))
				return storedCustomer;
		}
		return null;
	}

}
