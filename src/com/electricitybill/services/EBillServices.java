package com.electricitybill.services;

import java.util.List;

import com.electricitybill.domain.Customer;

public interface EBillServices {
	List<Customer> searchCustomer(String str);
	void generateBill(String cNumber);
}
