package com.electricitybill.services;

import java.util.List;

import com.electricitybill.domain.Customer;

public interface EBillServices {
	boolean addCustomer();

	void display();

	List<Customer> searchCustomer(String str);

	void generateBill(String cNumber);

	void searchBill(String billNumber);
	
	boolean payTotalBill(String cNumber);
	
	
}
