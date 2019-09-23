
package com.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.server.model.Account;
import com.server.model.Address;
import com.server.model.Customer;

@Path("/micro-customers")
public class MicroService {
	private static Map<Integer, Customer> customerDB;
	private static AtomicInteger idCounter;

	static {
		System.out.println("The initilaization for customerDB");
		customerDB = new ConcurrentHashMap<Integer, Customer>();
		idCounter = new AtomicInteger();
		Customer cust1 = new Customer();

		int idCount = idCounter.incrementAndGet();
		cust1.setFirstName("Asoka");
		cust1.setLastName("Mourya");
		cust1.setId(idCount);		 

		customerDB.put(idCount, cust1);
		idCount = idCounter.incrementAndGet();

		Customer cust2 = new Customer();
		cust2.setFirstName("Vijay");
		cust2.setLastName("Babu");
		cust2.setId(idCount);
		customerDB.put(idCount, cust2);

	}

	public MicroService() {
		System.out.println("MicroService.constructor()");

	}

	@GET
	@Produces({ "application/json" })
	public List getAllCustomers() {
		System.out.println("getAllCustomers from json Micro-service");
		int size = customerDB.size();
		System.out.println("The number of initial customer entries are " + size);
		System.out.println("The initial customer entries are " + customerDB);

		List<Customer> customerList = new ArrayList<Customer>(customerDB.values());
		System.out.println("The RAW customer list in Customer Micro-service is  " + customerList);

		//System.out.println("Now fectch address and account data from other external services");

		/*
		 * ListIterator<Customer> custIterator = customerList.listIterator(); for
		 * (Customer customer : customerList) {
		 * System.out.println("The current customer id is "+customer.getId());
		 * 
		 * Address current = fectchAddressFromAddessService(customer.getId());
		 * System.out.println("The address location found is " + current.getCity());
		 * 
		 * Account accountObj = fectchAccountFromAccountService(customer.getId());
		 * System.out.println("The customer account number found is " +
		 * accountObj.getAcNumber());
		 * 
		 * customer.setAddress(current); customer.setAccount(accountObj); }
		 * System.out.println("The final customerList returned \n");
		 * 
		 * ListIterator<Customer> iterator = customerList.listIterator(); for (Customer
		 * customer : customerList) {
		 * System.out.println("The cuastomer address location is " +
		 * customer.getAddress().getCity());
		 * System.out.println("The customer account number is " +
		 * customer.getAccount().getAcNumber()); }
		 */
		
		return customerList;
	}

	private Address fectchAddressFromAddessService(int customerId) {

		 
		// convert string in json format to Java object with Jackson lib
		ObjectMapper mapper = new ObjectMapper();
		Address obj = null;

		Address currentAddress = null;
		URL addressUrl;
		try {
			addressUrl = new URL("http://localhost:8090/Address-Micro-Service/address");
			// JSON from URL to Object
			// obj = mapper.readValue(addressUrl, com.server.model.Address.class);

			Address[] objects = mapper.readValue(addressUrl, Address[].class);
			System.out.println("No of addresses are " + objects.length);

			for (Address current : objects) {
				System.out.println(current.getId());
				int addressId = current.getId();
				if (customerId == addressId) {
					currentAddress = current;
					break;
				}

			}
			System.out.println("The final address is " + currentAddress.getCity());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentAddress;

	}
	
	private Account fectchAccountFromAccountService(int customerId) {

		 
		ObjectMapper mapper = new ObjectMapper();
		Address obj = null;

		Account currentAccount = null;
		URL accountUrl;
		try {
			accountUrl = new URL("http://localhost:8090/Account-Micro-Service/accounts");
			// JSON from URL to Object
			// obj = mapper.readValue(addressUrl, com.server.model.Address.class);

			Account[] objects = mapper.readValue(accountUrl, Account[].class);
			System.out.println("No of accounts are " + objects.length);

			for (Account current : objects) {
				System.out.println(current.getId());
				int addressId = current.getId();
				if (customerId == addressId) {
					currentAccount = current;
					break;
				}

			}
			System.out.println("The current account is " + currentAccount.getAcNumber());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentAccount;

	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json" })
	public Customer getCustomer(@PathParam("id") int id) {
		System.out.println("getCustomer for json " + id);
		int size = customerDB.size();
		System.out.println("The customer entries are " + size);
		Customer customer = customerDB.get(id);
		if (customer == null) {
			// throw new WebApplicationException(Response.Status.NOT_FOUND);
			System.out.println("The Customer NOT identified as  " + customer);
			return null;
		}
		System.out.println("\nCustomerResource.getCustomer() in json for " + customer.getId());
		System.out.println("The Customer identified as  " + customer.getFirstName());
		return customer;
	}
	
	
	@GET
	@Path("/{id}/address")
	@Produces({ "application/json" })
	public Address getCustomerAddress(@PathParam("id") int id) {		 
		int size = customerDB.size();	 
		Customer customer = customerDB.get(id);
		if (customer == null) {
			// throw new WebApplicationException(Response.Status.NOT_FOUND);
			System.out.println("The Customer NOT identified as  " + customer);
			return null;
		}	 
		
		 Address address = fectchAddressFromAddessService(customer.getId());
		 System.out.println("The address location found is " + address.getCity());		 
		 return address;
	}
	
	@GET
	@Path("/{id}/account")
	@Produces({ "application/json" })
	public Account getCustomerAccount(@PathParam("id") int id) {		 
		int size = customerDB.size();	 
		Customer customer = customerDB.get(id);
		if (customer == null) {			 
			System.out.println("The Customer NOT identified as  " + customer);
			return null;
		}	 
		
		Account accountObj = fectchAccountFromAccountService(customer.getId());
		System.out.println("The customer account number found is " +
		accountObj.getAcNumber());		  	  
		return accountObj;
	}

	@GET
	@Path("/{id}/address-with-account")
	@Produces({ "application/json" })
	public Customer getCustomerData(@PathParam("id") int id) {
		 
		Customer customer = customerDB.get(id);
		if (customer == null) {			 
			System.out.println("The Customer NOT identified as  " + customer);
			return null;
		}	 
		
		Account accountObj = fectchAccountFromAccountService(customer.getId());
		System.out.println("The customer account number found is " +
		accountObj.getAcNumber());
		customer.setAccount(accountObj); 
		
		Address address = fectchAddressFromAddessService(customer.getId());
		System.out.println("The address location found is " + address.getCity());	
		customer.setAddress(address); 		 
		return customer;
	}
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces({ "application/json" })
	// public Response createCustomerInJSON(Customer customer)
	// //Here the angularJS on client side expects the same object as json in the
	// response output
	public Customer createCustomerInJSON(Customer customer) {
		System.out.println("CustomerResource.createCustomer() post from json " + customer);

		int idCount = idCounter.incrementAndGet();

		/*
		 * System.out.println("The posted customer address is  "+customer.getAddress());
		 * System.out.println("The posted customer account is  "+customer.getAccount());
		 */

		//Read Customer address and account object entries
		//and POST them to respective account and address services
				
		customer.setId(idCount);
		customerDB.put(idCount, customer);

		Customer custObj = customerDB.get(idCount);
		System.out.println("The customer in map is  " + customer);
		System.out.println("Created customer " + idCount);

		int size = customerDB.size();
		System.out.println("The total customer entries now are " + size);

		// return Response.status(201).entity(cust_id).build();
		return customer;

	}

	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces({ "application/json" })
	// public Response updateCustomerInJSON(@PathParam("id") String id,Customer
	// customer)
	// Here the angularJS on client side expects the same object as json in the
	// respoinse output

	public Customer updateCustomerInJSON(@PathParam("id") String id, Customer customer) {
		System.out.println("CustomerResource.updateCustomer() put in json " + customer);

		int cust_id = customer.getId();
		System.out.println("Updating the customer with id = " + id);
		Customer custObj = customerDB.get(cust_id);
		System.out.println("The customer in map is  " + custObj);
		if (custObj == null)
			return null;

		//Read Customer address and account object entries
		//and update with PUT to respective account and address services
		
		customerDB.put(cust_id, customer);

		System.out.println("Updated customer " + cust_id);
		String result = "Updated customer " + cust_id;
		int size = customerDB.size();
		System.out.println("The customer entries in update are " + size);

		// return Response.status(201).entity(result).build();
		// Here the angularJS on client side expects the same object as json in the
		// respoinse output
		return customer;

	}

	@DELETE
	@Path("/{id}")
	// Here the angularJS on client side expects the same object as json in the
	// respoinse output
	// public Response removeCustomerInJSON(@PathParam("id") String id)

	public Customer removeCustomerInJSON(@PathParam("id") int id) {
		System.out.println("CustomerResource.removeCustomerInJSON()  in json for " + id);

		Customer custObj = customerDB.get(id);
		System.out.println("The customer in map is  " + custObj);
		if (custObj == null)
			return null;

		
		//Read Customer address and account object entries
		//and remove them  with delete method from  respective account and address services
				
		customerDB.remove(id);

		String result = "Removed customer  with " + id;
		int size = customerDB.size();
		System.out.println("The customer entries after  delete  are " + size);
		// return Response.status(201).entity(result).build();

		return custObj;

	}

	@GET
	@Path("{id}")
	@Produces("text/plain")
	public String getCustomerString(@PathParam("id") int id) {
		System.out.println("\nCustomerResource.getCustomerString() in plain text for  " + id);
		Customer cust = getCustomer(id);
		System.out.println("The customer in plain text is  " + cust);
		if (cust == null)
			return null;
		else
			return cust.toString();
	}
}
