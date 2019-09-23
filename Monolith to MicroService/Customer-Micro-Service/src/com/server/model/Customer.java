package com.server.model;

public class Customer {
	private int id;
	private String firstName;
	private String lastName;

	private Address address;

	private Account account;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
		;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer(int id, String firstName, String lastName) {
		System.out.println("Customer. params Customer()");
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Customer(int id, String firstName, String lastName, Address address, Account account) {
		System.out.println("Customer. address account customer()");
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.account = account;
	}

	public Customer() {
		System.out.println("Customer default ");
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	 

}
