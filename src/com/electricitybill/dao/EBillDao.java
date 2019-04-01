package com.electricitybill.dao;

import java.util.List;

import com.electricitybill.domain.Bill;
import com.electricitybill.domain.Customer;

public interface EBillDao {
	int getCustomersCount();
	int getBillCount();
	boolean addUser(Customer c);
	List<Customer> getAllCustomers();
	int getTotalUnits(String Cnumber);
	boolean updateUnitsConsumed(String Cnumber,int units);
	boolean addBill(Bill b);
}
