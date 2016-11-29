package com.raz.controller;

import com.raz.domain.Customer;
import com.raz.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CustomerControllerTest {

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController controller;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void listAllCustomers() throws Exception {
		Customer c1 = new Customer(1, "name1", "mail@some.com");
		Customer c2 = new Customer(2, "asdf", "ehm@bla.it");
		List<Customer> customerList = new ArrayList<>();
		customerList.add(c1);
		customerList.add(c2);

		when(customerService.getAllCustomers()).thenReturn(customerList);

		mockMvc.perform(get("/customers/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("customers"))
				.andExpect(model().attribute("customers", hasSize(2)));

		verify(customerService, times(1)).getAllCustomers();
	}

	@Test
	public void getCustomer() throws Exception {
		Customer c = new Customer(1, "name1", "mail@some.com");

		when(customerService.getCustomerById(eq(c.getId()))).thenReturn(c);

		mockMvc.perform(get("/customer/get/" + c.getId()))
				.andExpect(status().isOk())
				.andExpect(view().name("customer"))
				.andExpect(model().attribute("customer", instanceOf(Customer.class)))
				.andExpect(model().attribute("customer", equalTo(c)));

		verify(customerService, times(1)).getCustomerById(eq(c.getId()));
	}

	@Test
	public void editCustomer() throws Exception {
		Customer c = new Customer(1, "name1", "mail@some.com");

		when(customerService.getCustomerById(eq(c.getId()))).thenReturn(c);

		mockMvc.perform(get("/customer/edit/" + c.getId()))
				.andExpect(status().isOk())
				.andExpect(view().name("form"))
				.andExpect(model().attribute("customer", instanceOf(Customer.class)))
				.andExpect(model().attribute("customer", equalTo(c)));

		verify(customerService, times(1)).getCustomerById(eq(c.getId()));
	}

	@Test
	public void createNewCustomer() throws Exception {
		mockMvc.perform(get("/customer/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("form"))
				.andExpect(model().attribute("customer", instanceOf(Customer.class)));

		verifyZeroInteractions(customerService);
	}

	@Test
	public void saveCustomer() throws Exception {
		Customer c = new Customer(1, "name1", "mail@some.com");

		when(customerService.saveOrUpdateCustomer(eq(c))).thenReturn(c);

		mockMvc.perform(post("/customer")
				.param("id", c.getId().toString())
				.param("name", c.getName())
				.param("email", c.getEmail()))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/customer/" + c.getId()))
					.andExpect(model().attribute("customer", instanceOf(Customer.class)))
					.andExpect(model().attribute("customer", equalTo(c)));

		verify(customerService, times(1)).saveOrUpdateCustomer(eq(c));
	}

	@Test
	public void deleteCustomer() throws Exception {
		Integer id = 1;
		mockMvc.perform(get("/customer/delete/" + id))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/customers"));

		verify(customerService, times(1)).deleteCustomer(eq(id));
	}

}