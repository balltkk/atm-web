package th.ac.ku.atm.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import th.ac.ku.atm.model.Customer;

@Service
public class CustomerService {

	private static List<Customer> customerList;

	@PostConstruct
	public void postConstruct() {
		if (CustomerService.customerList == null)
			CustomerService.customerList = new ArrayList<>();
	}

	public void createCustomer(Customer customer) {
		String hashPin = hash(customer.getPin());
		customer.setPin(hashPin);
		customerList.add(customer);
	}

	public List<Customer> getCustomers() {
		return new ArrayList<>(CustomerService.customerList);
	}

	private String hash(String pin) {
		String salt = BCrypt.gensalt(12);
		return BCrypt.hashpw(pin, salt);
	}

	public Customer findCustomer(int id) {
		for (Customer customer : customerList) {
			if (customer.getId() == id)
				return customer;
		}
		return null;
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
