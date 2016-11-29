package com.raz.controller;

import com.raz.domain.Customer;
import com.raz.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping("/customers")
	public String listAllCustomers(Model model) {
		model.addAttribute("customers", customerService.getAllCustomers());

		return "customers";
	}

	@RequestMapping("/customer/{id}")
	public String getCustomer(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getCustomerById(id));

		return "customer";
	}

	@RequestMapping("/customer/edit/{id}")
	public String editCustomer(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getCustomerById(id));

		return "form";
	}

	@RequestMapping("/customer/new")
	public String createNewCustomer(Model model) {
		model.addAttribute("customer", new Customer());

		return "form";
	}

	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public String saveCustomer(Customer customer) {
		Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);

		return "redirect:/customer/" + savedCustomer.getId() ;
	}

	@RequestMapping("/customer/delete/{id}")
	public String deleteCustomer(@PathVariable Integer id){
		customerService.deleteCustomer(id);

		return "redirect:/customers";
	}
}
