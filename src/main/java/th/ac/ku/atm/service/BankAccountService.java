package th.ac.ku.atm.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Customer;

@Service
public class BankAccountService {
	
	private List<BankAccount> bankAccountList;
	
	@PostConstruct
	public void postConstruct() {
		this.bankAccountList = new ArrayList<>();
	}
	
	public boolean createBankAccount(BankAccount bankAccount, List<Customer> customerList) {
		boolean isCreateAccount = false;
		Customer customer = findCustomer(customerList, bankAccount.getCustomerId());
		if (customer != null) {
			bankAccountList.add(bankAccount);
			isCreateAccount = true;
		}
		return isCreateAccount;
	}
	
	public List<BankAccount> getBankAccountList() {
		return bankAccountList;
	}
	
	public Customer findCustomer(List<Customer> customerList, int id) {
		for (Customer customer : customerList) {
			if (customer.getId() == id)
				return customer;
		}
		return null;
	}
	
}
