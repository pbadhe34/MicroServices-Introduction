package com.server.model;
 
 
public class Customer
{
   private int id;   
   private String firstName;   
   private String lastName;
   private String cityName;   
   private int accountNumber;
   
   private Address address;
   
   private Account account;
   
   
   
   
   public Address getAddress() {
	return address;
}
public void setAddress(Address address) {
	this.address = address;
	cityName = this.address.getCity();
}
public Account getAccount() {
	return account;
}
public void setAccount(Account account) {
	this.account = account;
	this.accountNumber=account.getAcNumber();
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
     
   public Customer(int  id,
		   String firstName,
		   String  lastName  )
		{
	   System.out.println("Customer. params Customer()"); 
        this.id = id;
        this.firstName = firstName;
        this.lastName= lastName;         
   } 
   
   public Customer(int  id,
		   String firstName,
		   String  lastName ,Address address, Account account )
		{
	   System.out.println("Customer. address account customer()"); 
        this.id = id;
        this.firstName = firstName;
        this.lastName= lastName;   
        this.address = address;
        this.account = account;
   } 
   
   public Customer()
   {
	   System.out.println("Customer default ");
   }    
   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }    
   public String getLastName()
   {
      return lastName;
   }
   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }    
    
   public String toString()
   {
      /*return "{id:" +id+
               ",firstName:'" + firstName + '\'' +
              ", lastName:'" + lastName + '\'' +
              ", street:'" + getStreet() + '\'' +
              ", houseNumber:'" + getHouseNumber() + '\'' +              
              ", city:'" + getCity() + '\'' +
              ", accNumber:'" + getAcNumber() + '\'' + 
              ", branch:'" + getBranch()+ '\'' + 
              '}';*/
	   
	   return "{\"id\":" +id+
               ",\"firstName\":"+"\"" + firstName + "\"" +
              ", \"lastName\":"+"\"" + lastName + "\"" +
              ", \"street\":"+"\""+ getStreet() +"\"" +
              ", \"houseNumber\":" + getHouseNumber() +              
              ", \"city\":\"" + getCity() + "\"" +
              ", \"acNumber\":"+ getAcNumber()  + 
              ", \"branch\":"+"\""+ getBranch()+ "\"" + 
              "}";
   }
   
   public int getAcNumber() {
	   /*if(isAccount())
	     return this.account.getAcNumber() ;
	   else return 0;*/
	   return accountNumber;
	}

	public void setAcNumber(int acNumber) {
		 /*if(isAccount())
		     this.account.setAcNumber(acNumber) ;*/
		this.accountNumber= acNumber;
	}

	public String getBranch() {
		 if(isAccount())
		   return this.account.getBranch();
		 else return null;
	}

	public void setBranch(String branch) {
		 if(isAccount())
			 this.account.setBranch(branch);
	}
   private boolean isAddress()
   {
	   if(this.address==null)
		   return false;
	   else
		   return true;
   }
   
   private boolean isAccount()
   {
	   if(this.account==null)
		   return false;
	   else
		   return true;
   }
   public String getStreet()
   {
	   if(isAddress())
      return address.getStreet();
	   else return null;
   }

   public void setStreet(String street)
   {
	   if(isAddress())
		   address.setStreet(street);       
   }
    
   public String getCity()
   {
	   /*if(isAddress())
       return address.getCity();
	   else return null;*/
	   return cityName;
	   
   }

   public void setCity(String city)
   {
	   /*if(isAddress())
		   address.setCity(city);   */
	   cityName = city;
   }   
    
   public int getHouseNumber() {
	   if(isAddress())
	   return address.getHouseNumber() ;
	   else return 0;
	}

	public void setHouseNumber(int houseNumber) {
		 if(isAddress())
		address.setHouseNumber(houseNumber);  
	}
   
   
}
