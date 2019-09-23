
 package com.services;
 
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.server.model.Account;
import com.server.model.Address;
import com.server.model.Customer;

@Path("/customers")
public class CustomerService
{
    private static Map<Integer, Customer> customerDB;   
    private static AtomicInteger idCounter;   
   
   
   static
   {
	   System.out.println("The initilaization for customerDB");
 	   customerDB = new ConcurrentHashMap<Integer, Customer>();
	   idCounter = new AtomicInteger();
	   Customer cust1 = new Customer();	   
	   
	   int idCount = idCounter.incrementAndGet();	   
	   cust1.setFirstName("Asoka");  
	   cust1.setLastName("Mourya");
	   cust1.setId(idCount);
	   
	   Address address = new Address(12,345,"M G Road","Pune");
	   Account account = new Account(12,6578,"Mumabi");
	   
	   cust1.setAccount(account);
	   cust1.setAddress(address);
	   customerDB.put( idCount, cust1);
	   
	   
	   idCount = idCounter.incrementAndGet();    
	   
	   Customer cust2 = new Customer();
	   cust2.setFirstName("Vijay");  
	   cust2.setLastName("Babu");
	   cust2.setId(idCount);
	   Address address2 = new Address(11,34,"Center street","Kolaba");
	   Account account2 = new Account(10,765,"Pune");
	   
	   cust2.setAccount(account2);
	   cust2.setAddress(address2);
	   customerDB.put( idCount, cust2);    
	  
	  
	   
   }
   

   public CustomerService()
   {
	   System.out.println("CustomerService.init()");
	   
   }
   
   @GET     
   @Produces({"application/json"})       
   public List getAllCustomers()
   {
	  System.out.println("getAllCustomers from json service");
	  int size = customerDB.size();
	  System.out.println("The number of initial customer entries are "+size);	  
	  System.out.println("The initial customer entries are "+customerDB);	  
	  
	  List<Customer>customerList = new ArrayList<Customer>(customerDB.values());
	  System.out.println("The customer list is  "+customerList);  
       
	  
	  /*
	    * The returned list works with array  data filtering on client side.And also with
	    * AngularJS resource we DoNOT have to add isArray:false congiguration for query method
	    */
	  return customerList;
   }
   @GET   
   @Path("/{id}")
   @Produces({"application/json"})    
   public Customer getCustomer(@PathParam("id") int id)
   {
	  System.out.println("getCustomer for json "+id);
	  int size = customerDB.size();
	  System.out.println("The customer entries are "+size);
	  Customer customer = customerDB.get(id);	
	  if (customer == null)
      {
        // throw new WebApplicationException(Response.Status.NOT_FOUND);
		  System.out.println("The Customer NOT identified as  "+customer);
	      
		  return null;
      }
	  System.out.println("\nCustomerResource.getCustomer() in json for "+customer.getId());
     
      
      System.out.println("The Customer identified as  "+customer.getFirstName());
      
      return customer;
   }
   

   
   @POST
   @Path("/")	 
   @Consumes("application/json")
   @Produces({"application/json"})    
   //public Response createCustomerInJSON(Customer customer)
    // //Here the angularJS on client side expects the same object as json in the response output
   public Customer createCustomerInJSON(Customer customer)
   {
	  System.out.println("CustomerResource.createCustomer() post from json "+customer);
      
	  int idCount = idCounter.incrementAndGet(); 
	  
	/*  System.out.println("The posted customer address is  "+customer.getAddress());
	  System.out.println("The posted customer account is  "+customer.getAccount());
	*/  
	   
	  customer.setId(idCount);
      customerDB.put(idCount, customer);
      
      Customer custObj = customerDB.get(idCount);
      System.out.println("The customer in map is  "+customer);
      System.out.println("Created customer " + idCount);
       
      int size = customerDB.size();
	  System.out.println("The total customer entries now are "+size);
	   
      //return Response.status(201).entity(cust_id).build();
	  return customer;
      
   }

   @PUT
   @Path("/{id}")
   @Consumes("application/json")
   @Produces({"application/json"}) 
   //public Response updateCustomerInJSON(@PathParam("id") String id,Customer customer)
   //Here the angularJS on client side expects the same object as json in the respoinse output
	
   public Customer updateCustomerInJSON(@PathParam("id") String id,Customer customer)
   {
	  System.out.println("CustomerResource.updateCustomer() put in json "+customer);
      
	  int cust_id = customer.getId();
	  System.out.println("Updating the customer with id = "+id);
	  Customer custObj = customerDB.get(cust_id);
      System.out.println("The customer in map is  "+custObj);
      if(custObj==null)
    	  return null;
      
      customerDB.put(cust_id, customer);
     
      System.out.println("Updated customer " + cust_id);
      String result = "Updated customer " + cust_id;
      int size = customerDB.size();
	  System.out.println("The customer entries in update are "+size);     
	  
     // return Response.status(201).entity(result).build();
	  //Here the angularJS on client side expects the same object as json in the respoinse output
	  return customer;
      
   }

   
   @DELETE
   @Path("/{id}")   
   //Here the angularJS on client side expects the same object as json in the respoinse output
   //public Response removeCustomerInJSON(@PathParam("id") String id)

   public Customer removeCustomerInJSON(@PathParam("id") int id)
   {
	  System.out.println("CustomerResource.removeCustomerInJSON()  in json for "+id);     
	  
	  Customer custObj = customerDB.get(id);
      System.out.println("The customer in map is  "+custObj);
      if(custObj==null)
    	  return null;
      
      customerDB.remove(id);    
      
      String result = "Removed customer  with " + id;
      int size = customerDB.size();
	  System.out.println("The customer entries after  delete  are "+size);
     // return Response.status(201).entity(result).build();
	  
	  return custObj;
      
   }
   @GET   
   @Path("{id}")
   @Produces("text/plain")
   public String getCustomerString(@PathParam("id") int id)
   {
	  System.out.println("\nCustomerResource.getCustomerString() in plain text for  "+id);
	  Customer cust = getCustomer(id);
	  System.out.println("The customer in plain text is  "+cust);
	  if(cust==null)
		  return null;
	  else
		  return cust.toString();
   }
}
