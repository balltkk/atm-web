package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.service.BankAccountService;
import th.ac.ku.atm.service.CustomerService;

@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {

	private CustomerService customerService;
	private BankAccountService bankAccountService;

	public BankAccountController(CustomerService customerService, BankAccountService bankAccountService) {
		this.customerService = customerService;
		this.bankAccountService = bankAccountService;
	}

	@GetMapping
	public String getBankAccountPage(Model model) {
		model.addAttribute("allBankAccount", bankAccountService.getBankAccountList());
		return "bankaccount";
	}

	@PostMapping
	public String registerBankAccount(@ModelAttribute BankAccount bankAccount, Model model) {
		if (bankAccountService.createBankAccount(bankAccount, customerService.getCustomers())) {
			model.addAttribute("allBankAccount", bankAccountService.getBankAccountList());
			return "redirect:bankaccount";
		} else {
			model.addAttribute("greeting", "Can't find customer");
			return "home";
		}
	}

}
