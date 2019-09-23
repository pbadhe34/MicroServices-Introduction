
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

 
import com.server.model.Address;
 

@Path("/address")
public class AddressService
{
    private static Map<Integer, Address> addressDB;   
    private static AtomicInteger idCounter;   
   
   
   static
   {
	   System.out.println("The initilaization for addressDB");
 	   addressDB = new ConcurrentHashMap<Integer, Address>();
	   idCounter = new AtomicInteger();
	   Address adr1 = new Address(12,674,"Nehru Marg","Bengaluru");	   
	   
	   int idCount = idCounter.incrementAndGet();	   
	    adr1.setId(idCount);	   
	    
	   addressDB.put( idCount, adr1);	   
	   
	   idCount = idCounter.incrementAndGet();    	   
      Address adr2 = new Address(11,512," Netaji Bose Road Marg","Kolkata");	  
	   
	    adr2.setId(idCount);	 	    
	  
	   addressDB.put( idCount, adr2);   
	  
	  
	   
   }
   

   public AddressService()
   {
	   System.out.println("AddressService.init()");
	   
   }
   
   @GET     
   @Produces({"application/json"})       
   public Response getAllAddresses()
   {
	  System.out.println("getAllAddresses from address service");
	  int size = addressDB.size();
	  System.out.println("The number of initial customer entries are "+size);	  
	  System.out.println("The initial customer entries are "+addressDB);	  
	  
	  List<Address>addressList = new ArrayList<Address>(addressDB.values());
	  System.out.println("The address list is  "+addressList);  
       //Returna json as array of objects.
	  
	 // return Response.ok(customerList).build();
	  
	  GenericEntity entity = new GenericEntity<List<Address>>(addressList) {};
	    return Response.ok(entity).build();  
	     

   }
   
   /*@GET     
   @Produces({"application/json"})       
   public List getAllAddresses()
   {
	  System.out.println("getAllAddresses from adfress service");
	  int size = addressDB.size();
	  System.out.println("The number of initial customer entries are "+size);	  
	  System.out.println("The initial customer entries are "+addressDB);	  
	  
	  List<Address>customerList = new ArrayList<Address>(addressDB.values());
	  System.out.println("The address list is  "+customerList);  
       
	  
	  
	    * The returned list works with array  data filtering on client side.And also with
	    * AngularJS resource we DoNOT have to add isArray:false congiguration for query method
	    
	  return customerList;
   }*/
   @GET   
   @Path("/{id}")
   @Produces({"application/json"})    
   public Address getAddress(@PathParam("id") int id)
   {
	  System.out.println("getAddress from  AddressService "+id);
	  int size = addressDB.size();
	  System.out.println("The address  entries are "+size);
	  Address customer = addressDB.get(id);	
	  if (customer == null)
      {
        // throw new WebApplicationException(Response.Status.NOT_FOUND);
		  System.out.println("The Addrtess NOT identified as  "+customer);
	      
		  return null;
      }
	  System.out.println("\nAdddressResource.getCustomer() in json for "+customer.getId());
	  System.out.println("The Address   identified as  "+customer);
	    
      
        
      return customer;
   }
   

   
   @POST
   @Path("/")	 
   @Consumes("application/json")
   @Produces({"application/json"})    
   //public Response createCustomerInJSON(Customer customer)
    // //Here the angularJS on client side expects the same object as json in the response output
   public Address createAddressInJSON(Address customer)
   {
	  System.out.println("AddressResource.createAddress() post from json "+customer);
      
	  int idCount = idCounter.incrementAndGet(); 
	   
	  customer.setId(idCount);
      addressDB.put(idCount, customer);
      
      Address custObj = addressDB.get(idCount);
      System.out.println("The address  in map is  "+custObj);
      System.out.println("Created Address " + idCount);
       
      int size = addressDB.size();
	  System.out.println("The Address entries in poste are "+size);
	   
      //return Response.status(201).entity(cust_id).build();
	  return custObj;
      
   }

   @PUT
   @Path("/{id}")
   @Consumes("application/json")
   @Produces({"application/json"}) 
   //public Response updateCustomerInJSON(@PathParam("id") String id,Customer customer)
   //Here the angularJS on client side expects the same object as json in the respoinse output
	
   public Address updateCustomerInJSON(@PathParam("id") int id,Address customer)
   {
	  System.out.println("AddressResource.updateAddress() put in json "+customer);
      
	  int cust_id = customer.getId();
	  System.out.println("Updating the Address with id = "+id);
	  Address custObj = addressDB.get(cust_id);
      System.out.println("The address in map is  "+custObj);
      if(custObj==null)
    	  return null;
      
      addressDB.put(cust_id, customer);
     
      System.out.println("Updated Address " + cust_id);
      String result = "Updated Address " + cust_id;
      int size = addressDB.size();
	  System.out.println("The address entries in update are "+size);     
	  
     // return Response.status(201).entity(result).build();
	  //Here the angularJS on client side expects the same object as json in the respoinse output
	  return customer;
      
   }

   
   @DELETE
   @Path("/{id}")   
   //Here the angularJS on client side expects the same object as json in the respoinse output
   //public Response removeCustomerInJSON(@PathParam("id") String id)

   public Address removeCustomerInJSON(@PathParam("id") int id)
   {
	  System.out.println("AddressResource.removeCustomerInJSON()  in json for "+id);     
	  
	  Address custObj = addressDB.get(id);
      System.out.println("The address in map is  "+custObj);
      if(custObj==null)
    	  return null;
      
      addressDB.remove(id);    
      
      String result = "Removed address  with " + id;
      int size = addressDB.size();
	  System.out.println("The address entries after  delete  are "+size);
     // return Response.status(201).entity(result).build();
	  
	  return custObj;
      
   }
   @GET   
   @Path("{id}")
   @Produces("text/plain")
   public String getAddressString(@PathParam("id") int id)
   {
	  System.out.println("\nAddressResource.getAddressString() in plain text for  "+id);
	  Address cust = getAddress(id);
	  System.out.println("The address in plain text is  "+cust);
	  if(cust==null)
		  return null;
	  else
		  return cust.toString();
   }
}
