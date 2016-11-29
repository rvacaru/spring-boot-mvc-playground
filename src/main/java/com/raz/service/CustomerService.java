package com.raz.service;

import com.raz.domain.Customer;

import java.util.List;

public interface CustomerService {

	List<Customer> getAllCustomers();

	Customer getCustomerById(Integer id);

	Customer saveOrUpdateCustomer(Customer customer);

	void deleteCustomer(Integer id);
}
