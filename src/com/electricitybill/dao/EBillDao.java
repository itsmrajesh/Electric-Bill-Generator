package com.electricitybill.dao;

import java.util.List;

import com.electricitybill.domain.Bill;
import com.electricitybill.domain.Customer;

public interface EBillDao {
	boolean validateAdmin(String uName,String password);
	int getCustomersCount();
	int getBillCount();
	boolean addCustomer(Customer c);
	List<Customer> getAllCustomers();
	int getTotalUnits(String Cnumber);
	boolean updateUnitsConsumed(String cNumber,int units);
	boolean addBill(Bill b);
}
