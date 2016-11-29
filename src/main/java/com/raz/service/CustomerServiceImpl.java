package com.raz.service;

import com.raz.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Map<Integer, Customer> customerDb;

	public CustomerServiceImpl(){
		customerDb = new HashMap<>();
		createCustomers(customerDb);
	}

	@Override public List<Customer> getAllCustomers() {
		return new ArrayList<>(customerDb.values());
	}

	@Override public Customer getCustomerById(Integer id) {
		return customerDb.get(id);
	}

	@Override public Customer saveOrUpdateCustomer(Customer customer) {
		if(customer != null){
			if(customer.getId() == null){
				customer.setId(getNextIndex());
			}
			customerDb.put(customer.getId(), customer);
			return customerDb.get(customer.getId());
		} else{
			throw new IllegalArgumentException("Customer can't be null, bitch");
		}

	}

	private Integer getNextIndex() {
		return Collections.max(customerDb.keySet()) + 1;
	}

	@Override public void deleteCustomer(Integer id) {
		if(customerDb.containsKey(id)){
			customerDb.remove(id);
		}
	}

	private static void createCustomers(Map<Integer, Customer> customerDb) {
		Customer c1 = new Customer(1, "Fabio", "f.sti@cazzi.it");
		Customer c2 = new Customer(2, "Razz", "r.vak@meh.it");
		Customer c3 = new Customer(3, "Anna", "a.ianne@pako.it");

		customerDb.put(1, c1);
		customerDb.put(2, c2);
		customerDb.put(3, c3);
	}
}
