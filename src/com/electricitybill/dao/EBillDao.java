package com.electricitybill.dao;

import java.util.List;

import com.electricitybill.domain.Bill;
import com.electricitybill.domain.Customer;

public interface EBillDao {
	boolean validateAdminDao(String uName, String password);

	int getCustomersCount();

	int getBillCount();

	boolean addCustomerDao(Customer c);

	List<Customer> getAllCustomers();

	int getTotalUnits(String cNumber);

	boolean updateUnitsConsumed(String cNumber, int units);

	boolean addBillDao(Bill b);

	boolean isPaid(String billNumber);

	boolean payBillDao(String cNumber, String billNumber);

	boolean getBillDao(String billNumber);

	double getTotalBillDao(String cNumber);
}
