
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
 
@Path("/accounts")
public class AccountService
{
    private static Map<Integer, Account> accountDB;   
    private static AtomicInteger idCounter;   
   
   
   static
   {
	   System.out.println("The initilaization for AccountDB");
 	   accountDB = new ConcurrentHashMap<Integer, Account>();
	   idCounter = new AtomicInteger();
	   Account ac1 = new Account(12,4567,"SBI-Pune");
	   Account ac2 = new Account(11,874,"BOM-Pune");   
	   
	   
	   int idCount = idCounter.incrementAndGet();	   
	    ac1.setId(idCount);	   
	    
	   accountDB.put( idCount, ac1);	   
	   
	   idCount = idCounter.incrementAndGet();        
	   ac2.setId(idCount);    
	    
	  
	   accountDB.put( idCount, ac1);    
	   accountDB.put( idCount, ac2);    
	  
	  
	   
   }
   

   public AccountService()
   {
	   System.out.println("AccountService.init()");
	   
   }
   
   @GET     
   @Produces({"application/json"})       
   public List getAllAccounts()
   {
	  System.out.println("getAllAccounts from Account service");
	  int size = accountDB.size();
	  System.out.println("The number of initial account entries are "+size);	  
	  System.out.println("The initial account entries are "+accountDB);	  
	  
	  List<Account>AccountList = new ArrayList<Account>(accountDB.values());
	  System.out.println("The Account list is  "+AccountList);  	  
	  
	  return AccountList;
   }
   @GET   
   @Path("/{id}")
   @Produces({"application/json"})    
   public Account getAccount(@PathParam("id") int id)
   {
	  System.out.println("getAccount from  AccountService "+id);
	  int size = accountDB.size();
	  System.out.println("The account entries are "+size);
	  Account account = accountDB.get(id);	
	  if (account == null)
      {
        // throw new WebApplicationException(Response.Status.NOT_FOUND);
		  System.out.println("The Account NOT identified as  "+account);
	      
		  return null;
      }
	  System.out.println("\nAccountResource.getAccount() in json for "+account.getId());     
	  System.out.println("The Account   identified as  "+account);
	    
        
      return account;
   }
   

   
   @POST
   @Path("/")	 
   @Consumes("application/json")
   @Produces({"application/json"})    
   //public Response createaccountInJSON(account account)
    // //Here the angularJS on client side expects the same object as json in the response output
   public Account createAccountJSON(Account account)
   {
	  System.out.println("AccountResource.createAccount() post from json "+account);
      
	  int idCount = idCounter.incrementAndGet(); 
	   
	  account.setId(idCount);
      accountDB.put(idCount, account);
      
      Account custObj = accountDB.get(idCount);
      System.out.println("The Account  in map is  "+custObj);
      System.out.println("Created Account " + idCount);
       
      int size = accountDB.size();
	  System.out.println("The Account entries in poste are "+size);
	   
      //return Response.status(201).entity(cust_id).build();
	  return custObj;
      
   }

   @PUT
   @Path("/{id}")
   @Consumes("application/json")
   @Produces({"application/json"}) 
   //public Response updateaccountInJSON(@PathParam("id") String id,account account)
   //Here the angularJS on client side expects the same object as json in the respoinse output
	
   public Account updateAccountJSON(@PathParam("id") int id,Account account)
   {
	  System.out.println("AccountResource.updateAccount() put in json "+account);
      
	  int cust_id = account.getId();
	  System.out.println("Updating the Account with id = "+id);
	  Account custObj = accountDB.get(cust_id);
      System.out.println("The Account in map is  "+custObj);
      if(custObj==null)
    	  return null;
      
      accountDB.put(cust_id, account);
     
      System.out.println("Updated Account " + cust_id);
      String result = "Updated Account " + cust_id;
      int size = accountDB.size();
	  System.out.println("The Account entries in update are "+size);     
	  
     // return Response.status(201).entity(result).build();
	  //Here the angularJS on client side expects the same object as json in the respoinse output
	  return account;
      
   }

   
   @DELETE
   @Path("/{id}")   
   //Here the angularJS on client side expects the same object as json in the respoinse output
   //public Response removeaccountInJSON(@PathParam("id") String id)

   public Account removeAccountInJSON(@PathParam("id") int id)
   {
	  System.out.println("accountResource.removeaccountInJSON()  in json for "+id);     
	  
	  Account custObj = accountDB.get(id);
      System.out.println("The account in map is  "+custObj);
      if(custObj==null)
    	  return null;
      
      accountDB.remove(id);    
      
      String result = "Removed Account  with " + id;
      int size = accountDB.size();
	  System.out.println("The Account entries after  delete  are "+size);
     // return Response.status(201).entity(result).build();
	  
	  return custObj;
      
   }
   @GET   
   @Path("{id}")
   @Produces("text/plain")
   public String getAccountString(@PathParam("id") int id)
   {
	  System.out.println("\naccountResource.getaccountString() in plain text for  "+id);
	  Account cust = getAccount(id);
	  System.out.println("The account in plain text is  "+cust);
	  if(cust==null)
		  return null;
	  else
		  return cust.toString();
   }
}
